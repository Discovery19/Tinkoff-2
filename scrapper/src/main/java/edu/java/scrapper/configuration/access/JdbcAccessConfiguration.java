package edu.java.scrapper.configuration.access;

import edu.java.scrapper.api.repositories.ChatsRepository;
import edu.java.scrapper.api.repositories.LinksRepository;
import edu.java.scrapper.api.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.api.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.api.service.LinkService;
import edu.java.scrapper.api.service.TgChatService;
import edu.java.scrapper.api.service.jdbc.JdbcChatService;
import edu.java.scrapper.api.service.jdbc.JdbcLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
@RequiredArgsConstructor
public class JdbcAccessConfiguration {
    private final ChatsRepository jdbcChatRepository;
    private final LinksRepository jdbcLinkRepository;

    @Bean
    public LinkService jdbcLinkService(JdbcLinkRepository jdbcLinkRepository) {
        return new JdbcLinkService(jdbcLinkRepository);
    }

    @Bean
    public TgChatService jdbcChatService(JdbcChatRepository jdbcChatRepository) {
        return new JdbcChatService(jdbcChatRepository);
    }

}
