package edu.java.scrapper.db_tests;

import edu.java.api.repositories.JdbcChatsRepository;
import edu.java.api.repositories.dto.ChatDTO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class ChatsRepoTest extends IntegrationTest {

    @Autowired
    private JdbcChatsRepository chatRepository;

    @BeforeAll
    public static void setUp() {
        System.out.println("Database started");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
        jdbcTemplate.update("insert into links (url) values (?)", "link1.com");
        jdbcTemplate.update("insert into link_chat (link_id, chat_id) values (?, ?)", 1L, 1L);
    }

    @AfterAll
    public static void tearDown() {

    }

    @Test
    @Transactional
    @Rollback
    void createChatTest() {

        assertTrue(POSTGRES.isRunning());

        chatRepository.add(2L);

        var res = chatRepository.findAll();
        assertTrue(res.contains(new ChatDTO(2L)));
    }

    @Test
    @Transactional
    @Rollback
    void removeChatTest() {
        assertTrue(POSTGRES.isRunning());
        chatRepository.remove(1L);
        var res = chatRepository.findAll();
        assertTrue(res.isEmpty());
    }
}
