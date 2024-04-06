package edu.java.bot.configuration;

import edu.java.bot.client.ScrapperAPIClient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotNull
    String bootstrapServers,
    @NotNull
    String scrapperTopic
) {
    @Bean
    public ScrapperAPIClient getScrapperAPIClient() {
        return new ScrapperAPIClient("http://localhost:8080");
    }
}
