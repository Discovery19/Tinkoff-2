package edu.java.scrapper.db_tests;

import edu.java.api.repositories.jdbc.JdbcChatsRepository;
import edu.java.api.repositories.dto.ChatDTO;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ChatsRepoTest extends IntegrationTest {

    @Autowired
    private JdbcChatsRepository chatRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void createChatTest() {

        assertTrue(POSTGRES.isRunning());

        chatRepository.add(2L);

        var res = chatRepository.findAll();
        assertTrue(res.contains(new ChatDTO(2L)));
        jdbcTemplate.update("delete from chats");
    }

    @Test
    @Transactional
    @Rollback
    void removeChatTest() {
        assertTrue(POSTGRES.isRunning());
        jdbcTemplate.update("insert into chats (id) values (?)", 1L);
        chatRepository.remove(1L);
        var res = chatRepository.findAll();
        assertTrue(res.isEmpty());
    }


}
