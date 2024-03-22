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

}
