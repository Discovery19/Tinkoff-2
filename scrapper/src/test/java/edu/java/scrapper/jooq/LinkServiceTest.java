package edu.java.scrapper.jooq;

import edu.java.scrapper.api.requests.LinkRequest;
import edu.java.scrapper.api.response.api_response.LinkResponse;
import edu.java.scrapper.api.response.api_response.ListLinksResponse;
import edu.java.scrapper.api.service.LinkService;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LinkServiceTest extends IntegrationTest {
    @Autowired
    @Qualifier("jooqLinkService")
    LinkService service;

    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String EXAMPLE_URL = "https://link1.com/";
    private static final String EXAMPLE2_URL = "https://link2.com/";

    @Test
    @Transactional
    @Rollback
    void shouldAddLink() throws URISyntaxException {
        //given
        jdbcTemplate.update("insert into chats values (123)");

        //when
        service.trackLink(123L, new LinkRequest(URI.create(EXAMPLE_URL)));

        //then
        assertEquals(
            EXAMPLE_URL,
            jdbcTemplate.queryForObject(
                "select url from links join link_chat lca on links.id = lca.link_id",
                String.class
            )
        );
    }


    @Test
    @Transactional
    @Rollback
    void shouldReturnLinks() throws URISyntaxException {
        //given
        jdbcTemplate.update("insert into links(id, url) values (1, ?), (2, ?)", EXAMPLE_URL, EXAMPLE2_URL);
        jdbcTemplate.update("insert into chats(id) values (123)");
        jdbcTemplate.update("insert into link_chat(link_id, chat_id) values (1, 123), (2, 123)");

        //when
        var listResponse = service.getLinks(123L);

        //then
        assertEquals(
            new ListLinksResponse(List.of(
                new LinkResponse(1L, URI.create(EXAMPLE_URL)),
                new LinkResponse(2L, URI.create(EXAMPLE2_URL)))),
            listResponse.getBody()
        );
    }

    @Test
    @Transactional
    @Rollback
    void shouldReturnEmptyCollectionIfNoAssignments() throws URISyntaxException {
        jdbcTemplate.update("insert into chats(id) values (123)");

        Assertions.assertTrue(Objects.requireNonNull(service.getLinks(123L).getBody()).links().isEmpty());
    }


    @Test
    @Transactional
    @Rollback
    void shouldDeleteLink() throws URISyntaxException {
        //given
        jdbcTemplate.update("insert into links(id, url) values (1, ?)", EXAMPLE_URL);
        jdbcTemplate.update("insert into chats(id) values (123)");
        jdbcTemplate.update("insert into link_chat(link_id, chat_id) values (1, 123)");

        //when
        service.untrackLink(123L, new LinkRequest(URI.create(EXAMPLE_URL)));

        //then
        Assertions.assertTrue(
            jdbcTemplate.queryForList(
                "select chat_id from link_chat", Long.class
            ).isEmpty()
        );
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteUnassignedLinks() throws URISyntaxException {
        //given
        jdbcTemplate.update("insert into links(id, url) values (1, ?), (2, ?)", EXAMPLE_URL, EXAMPLE2_URL);
        jdbcTemplate.update("insert into chats(id) values (123), (1234)");
        jdbcTemplate.update("insert into link_chat(link_id, chat_id) values (1, 123), (1, 1234), (2, 123)");

        //when
        service.untrackLink(123L, new LinkRequest(URI.create(EXAMPLE2_URL)));

        //then
        assertEquals(
            EXAMPLE_URL,
            jdbcTemplate.queryForObject(
                "select distinct(url) from links join link_chat lca on links.id = lca.link_id",
                String.class
            )
        );
    }


}
