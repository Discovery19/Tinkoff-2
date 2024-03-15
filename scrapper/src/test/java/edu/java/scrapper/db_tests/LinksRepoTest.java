package edu.java.scrapper.db_tests;

import edu.java.api.repositories.JdbcLinksRepository;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LinksRepoTest extends IntegrationTest {
    @Autowired
    private JdbcLinksRepository linksRepository;

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
    void addLinkTest() throws URISyntaxException {

        assertTrue(POSTGRES.isRunning());

        linksRepository.add(1L, new URI("link.com"));

        var res = linksRepository.findAll(1L);
        assertEquals(2, res.size());
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkTest() throws URISyntaxException {
        assertTrue(POSTGRES.isRunning());

        linksRepository.remove(1L, new URI("link1.com"));

        var res = linksRepository.findAll(1L);
        assertTrue(res.isEmpty());
    }
}
