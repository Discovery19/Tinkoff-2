package edu.java.configuration.access;

import edu.java.api.repositories.jdbc.JdbcChatRepository;
import edu.java.api.repositories.jdbc.JdbcLinkRepository;
import edu.java.api.service.LinkService;
import edu.java.api.service.TgChatService;
import edu.java.api.service.jdbc.JdbcChatService;
import edu.java.api.service.jdbc.JdbcLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final JdbcChatRepository jdbcChatRepository;
    private final JdbcLinkRepository jdbcLinkRepository;

    @Bean
    public LinkService jdbcLinkService(JdbcLinkRepository jdbcLinkRepository) {
        return new JdbcLinkService(jdbcLinkRepository);
    }

    @Bean
    public TgChatService jdbcChatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

}
