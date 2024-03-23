package edu.java.api.repositories.jdbc;

import edu.java.api.repositories.ChatsRepository;
import edu.java.api.repositories.dto.ChatDTO;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class JdbcChatsRepository implements ChatsRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcChatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_SQL_MESSAGE_CHAT_TABLE = "insert into chats (id) values (?)";

    private boolean checkId(long id) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT COALESCE(id, -1) AS id FROM chats WHERE id = ?", Long.class, id) == 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public ChatDTO add(Long id) {
        if (checkId(id)) {
            return jdbcTemplate.queryForObject("SELECT id, name FROM chats WHERE id IN (?)",
                (rs, rowNum) ->
                    new ChatDTO(rs.getLong("id")), id
            );
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    ADD_SQL_MESSAGE_CHAT_TABLE,
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, id);
                return ps;
            }, keyHolder);

            Map<String, Object> keyMap = keyHolder.getKeyList().getFirst();

            return new ChatDTO((long) keyMap.get("id"));
        }
    }

    @Override
    @Transactional
    public ChatDTO remove(Long id) {
        jdbcTemplate.update("delete FROM chats WHERE id = (?)", id);
        return new ChatDTO(id);
    }

    @Override
    @Transactional
    public List<ChatDTO> findAll() {
        return jdbcTemplate.query(
            "select * from chats",
            (rs, rowNum) ->
                new ChatDTO(rs.getLong("id"))
        );
    }
}
