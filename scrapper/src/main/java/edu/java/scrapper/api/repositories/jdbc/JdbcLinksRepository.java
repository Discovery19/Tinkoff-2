package edu.java.scrapper.api.repositories.jdbc;

import edu.java.scrapper.api.repositories.LinksRepository;
import edu.java.scrapper.api.repositories.dto.LinkDTO;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
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
public class JdbcLinksRepository implements LinksRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String ADD_SQL_MESSAGE_LINKS_TABLE = "insert into links (url) values (?)";
    private static final String ADD_SQL_MESSAGE_LINK_CHAT_TABLE =
        "insert into link_chat (link_id, chat_id) values (?, ?)";

    private long checkUrl(String url) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT COALESCE(id, -1) AS id FROM links WHERE url = ?", Long.class, url);
        } catch (EmptyResultDataAccessException e) {
            return -1L;
        }
    }

    private long insertUrl(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps =
                connection.prepareStatement(ADD_SQL_MESSAGE_LINKS_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, url);
            return ps;
        }, keyHolder);

        List<Map<String, Object>> keyList = keyHolder.getKeyList();
        Map<String, Object> keyMap = keyList.get(0);
        Number generatedId = (Number) keyMap.get("id");
        return generatedId.longValue();

    }

    private void addBindings(long linkId, long chatId) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(ADD_SQL_MESSAGE_LINK_CHAT_TABLE);
            ps.setLong(1, linkId);
            ps.setLong(2, chatId);
            return ps;
        });
    }

    @Override
    @Transactional
    public LinkDTO add(Long chatId, URI url) {
        String link = String.valueOf(url);
        long linkId = checkUrl(link);

        if (linkId == -1L) {
            linkId = insertUrl(link);
        }
        addBindings(linkId, chatId);
        log.info("Added link " + linkId + " to chat" + chatId);
        return new LinkDTO(linkId, link);
    }

    @Override
    @Transactional
    public LinkDTO remove(Long chatId, URI url) {
        long linkId = checkUrl(String.valueOf(url));
        if (linkId == -1L) {
            log.error("No link found in links" + url);
        } else {
            jdbcTemplate.update(
                "delete from links where id = ?", linkId);
            jdbcTemplate.update("DELETE FROM link_chat WHERE link_id = ? AND chat_id = ?", linkId, chatId);
        }
        return new LinkDTO(linkId, String.valueOf(url));
    }

    @Override
    @Transactional
    public List<LinkDTO> findAll(Long chatId) {
        List<Long> linksIds =
            jdbcTemplate.queryForList("SELECT link_id FROM link_chat WHERE chat_id = ?", Long.class, chatId);
        if (linksIds.isEmpty()) {
            return new ArrayList<>();
        }
        String sqlGetAllLinks = "SELECT id, url FROM links WHERE id IN (";

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < linksIds.size(); i++) {
            placeholders.append("?");
            if (i < linksIds.size() - 1) {
                placeholders.append(",");
            }
        }
        sqlGetAllLinks += placeholders + ")";
        return jdbcTemplate.query(sqlGetAllLinks,
            (rs, rowNum) ->
                new LinkDTO(rs.getLong("id"), rs.getString("url")), linksIds.toArray()
        );
    }


}
