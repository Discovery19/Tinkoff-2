package edu.java.scrapper.configuration.access;

import edu.java.scrapper.api.service.LinkService;
import edu.java.scrapper.api.service.TgChatService;
import edu.java.scrapper.api.service.jooq.JooqChatService;
import edu.java.scrapper.api.service.jooq.JooqLinkService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
@RequiredArgsConstructor
public class JooqAccessConfiguration {
    private final DSLContext context;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public LinkService jdbcLinkService(DSLContext context) {
        return new JooqLinkService(context);
    }

    @Bean
    public TgChatService jooqTgChatService(DSLContext context) {
        return new JooqChatService(context);
    }
}
