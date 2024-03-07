package edu.java.api.repositories;

import edu.java.api.repositories.dto.DefaultAnswerDTO;
import java.net.URI;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class TgChatRepository implements LinksRepository {
    JdbcTemplate jdbcTemplate;

    public TgChatRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String ADD_SQL_MESSAGE_LINKS_TABLE = "insert into links (url) values (?)";
    private static final String ADD_SQL_MESSAGE_LINK_CHAT_TABLE =
        "insert into link_chat (linkId, chatId) values (?, ?)";

    public long checkUrl(String url) {
        return jdbcTemplate.queryForObject(
            "SELECT COALESCE(id, -1) AS id FROM links WHERE url = ?", Long.class, url);
    }

    public long insertUrl(String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(ADD_SQL_MESSAGE_LINKS_TABLE, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, url);
            return ps;
        }, keyHolder);
        return (long) keyHolder.getKey();
    }

    public void addBindings(long linkId, long chatId) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(ADD_SQL_MESSAGE_LINK_CHAT_TABLE);
            ps.setString(1, String.valueOf(linkId));
            ps.setString(2, String.valueOf(chatId));
            return ps;
        });
    }

    @Override
    @Transactional
    public DefaultAnswerDTO add(Long chatId, URI url) {
        String link = String.valueOf(url);
        long linkId = checkUrl(link);
        if (linkId == -1L) {
            linkId = insertUrl(link);
        }
        addBindings(linkId, chatId);
        log.info("Added link " + linkId + " to chat" + chatId);
        return null;
    }

    @Override
    @Transactional
    public DefaultAnswerDTO remove(Long chatId, URI url) {
        long linkId = checkUrl(String.valueOf(url));
        if (linkId == -1L) {
            log.error("No link found in links" + url);
        } else {
            jdbcTemplate.update(
                "delete from links where id = ?", linkId);
            jdbcTemplate.update("DELETE FROM link_chat WHERE link_id = ? AND chat_id = ?", linkId, chatId);
        }
        return null;
    }

    @Override
    @Transactional
    public void findAll(Long chatId) {
        List<Long> linksIds =
            jdbcTemplate.queryForList("SELECT link_id FROM link_chat WHERE chat_id = ?", Long.class, chatId);
        String sqlGetAllLinks = "SELECT url FROM links WHERE id IN (";

        // Добавляем плейсхолдеры для каждого link_id
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < linksIds.size(); i++) {
            placeholders.append(linksIds.get(i));
            if (i < linksIds.size() - 1) {
                placeholders.append(",");
            }
        }
        sqlGetAllLinks += placeholders + ")";
        List<String> linksUrls = jdbcTemplate.queryForList(sqlGetAllLinks, String.class);
        for (String url : linksUrls) {
            System.out.println(url);
        }
        // Выполняем запрос с использованием linkIds в качестве параметров
//        return jdbcTemplate.query(sqlGetAllLinks, (rs, rowNum) -> {
//            Link link = new Link();
//            link.setId(rs.getLong("id"));
//            link.setUrl(rs.getString("url"));
//            return link;
//        }, linkIds.toArray());
    }
}
