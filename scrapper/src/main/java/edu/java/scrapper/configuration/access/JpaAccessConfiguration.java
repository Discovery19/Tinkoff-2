package edu.java.scrapper.configuration.access;

import edu.java.scrapper.api.repositories.ChatsRepository;
import edu.java.scrapper.api.repositories.LinksRepository;
import edu.java.scrapper.api.repositories.jpa.JpaChatRepository;
import edu.java.scrapper.api.repositories.jpa.JpaLinkRepository;
import edu.java.scrapper.api.service.LinkService;
import edu.java.scrapper.api.service.TgChatService;
import edu.java.scrapper.api.service.jpa.JpaChatService;
import edu.java.scrapper.api.service.jpa.JpaLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
@RequiredArgsConstructor
public class JpaAccessConfiguration {
    private final ChatsRepository chatRepository;
    private final LinksRepository linkRepository;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public LinkService jdbcLinkService(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        return new JpaLinkService(jpaLinkRepository, jpaChatRepository);
    }

    @Bean
    public TgChatService jpaTgChatService(JpaChatRepository jpaChatRepository) {
        return new JpaChatService(jpaChatRepository);
    }

}