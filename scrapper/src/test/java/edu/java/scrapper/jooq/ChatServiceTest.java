package edu.java.scrapper.jooq;

import edu.java.api.service.TgChatService;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ChatServiceTest extends IntegrationTest {
    @Autowired
    @Qualifier("jooqChatService")
    TgChatService service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String EXAMPLE_URL = "https://link1.com/";
    private static final String EXAMPLE2_URL = "https://link2.com/";

    @Test
    @Transactional
    @Rollback
    void shouldRegisterChat() {
        service.registerChat(123L);

        Assertions.assertEquals(
            123L,
            jdbcTemplate.queryForObject("select id from chats where id = 123", Long.class)
        );
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteChat() {
        //given
        jdbcTemplate.update("insert into chats(id) values (123)");

        //when
        service.deleteChat(123L);

        //then
        Assertions.assertTrue(
            jdbcTemplate.queryForList("select id from chats where id = 123", Long.class).isEmpty()
        );
    }

    @Test
    @Transactional
    @Rollback
    void shouldDeleteUnassignedLinksIfChatDeleted() {
        //given
        jdbcTemplate.update("insert into links(id, url) values (1, ?), (2, ?)", EXAMPLE_URL, EXAMPLE2_URL);
        jdbcTemplate.update("insert into chats(id) values (123), (1234)");
        jdbcTemplate.update("insert into link_chat(link_id, chat_id) values (1, 123), (1, 1234), (2, 123)");

        //when
        service.deleteChat(123L);

        //then
        Assertions.assertTrue(
            jdbcTemplate.queryForList("select id from links where id = 2", Long.class).isEmpty()
        );
        Assertions.assertFalse(
            jdbcTemplate.queryForList("select id from links where id = 1", Long.class).isEmpty()
        );
    }

}
