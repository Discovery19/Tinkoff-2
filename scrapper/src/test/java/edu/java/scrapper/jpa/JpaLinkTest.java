package edu.java.scrapper.jpa;

import edu.java.api.repositories.jdbc.JdbcLinkRepository;
import edu.java.api.requests.LinkRequest;
import edu.java.api.service.LinkService;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
class JpaLinkTest extends IntegrationTest {
    @Autowired
    @Qualifier("jpaLinkService")
    private LinkService service;
    @Autowired
    private JdbcLinkRepository linksRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addLinkTest() throws URISyntaxException {

        assertTrue(POSTGRES.isRunning());
        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
        service.trackLink(1L, new LinkRequest(new URI("https://link.com")));

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
//        jdbcTemplate.update("insert into links (url) values (?)", "link.com");
        jdbcTemplate.update("insert into links (url) values (?)", "https://link1.com");
        service.untrackLink(1L, new LinkRequest(new URI("https://link1.com")));

        var res = linksRepository.findAll(1L);
        assertTrue(res.isEmpty());
    }
    @Test
    @Transactional
    @Rollback
    void getLinksTest() throws URISyntaxException {
        assertTrue(POSTGRES.isRunning());
        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
//        jdbcTemplate.update("insert into links (url) values (?)", "https://link1.com");
        service.trackLink(1L, new LinkRequest(new URI("https://link1.com")));
        var res = service.getLinks(1L).getBody().links();
        assertEquals("https://link1.com", res.getFirst().link().toString());
    }
}
