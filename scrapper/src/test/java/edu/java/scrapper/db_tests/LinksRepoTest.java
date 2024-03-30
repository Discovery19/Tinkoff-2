package edu.java.scrapper.db_tests;

import edu.java.scrapper.api.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LinksRepoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkRepository linksRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addLinkTest() throws URISyntaxException {

        assertTrue(POSTGRES.isRunning());
        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
        linksRepository.add(1L, new URI("link.com"));

        var res = linksRepository.findAll(1L);
        assertEquals(1, res.size());
        jdbcTemplate.update("delete from chats");
        jdbcTemplate.update("delete from links");
        jdbcTemplate.update("delete from link_chat");
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkTest() throws URISyntaxException {
        assertTrue(POSTGRES.isRunning());
        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
        jdbcTemplate.update("insert into links (url) values (?)", "link1.com");
        linksRepository.remove(1L, new URI("link1.com"));

        var res = linksRepository.findAll(1L);
        assertTrue(res.isEmpty());
    }
}
