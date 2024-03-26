package edu.java.scrapper.configuration;

import edu.java.scrapper.bot_api.client.BotAPIClient;
import edu.java.scrapper.client.github.GitHubWebClient;
import edu.java.scrapper.client.stackoverflow.StackOverflowWebClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    @Bean
    Scheduler scheduler
) {

    private static final String GITHUB_BASE_URL = "https://api.github.com";
    private static final String STACKOVERFLOW_BASE_URL = "https://api.stackexchange.com";
    private static final String BASE_URL = "http://localhost:8090";

    @Bean
    GitHubWebClient gitHubClient() {
        return new GitHubWebClient(GITHUB_BASE_URL);
    }

    @Bean
    StackOverflowWebClient stackOverflowClient() {
        return new StackOverflowWebClient(STACKOVERFLOW_BASE_URL);
    }

    @Bean
    public BotAPIClient botAPIClient() {
        return new BotAPIClient(BASE_URL);
    }

    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay,
                            @NotNull Duration linkCheckingFrequency) {
    }

}
