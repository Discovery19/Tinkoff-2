package edu.java.scrapper.configuration;

import edu.java.scrapper.client.BotAPIClient;
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
    Scheduler scheduler,
    @NotNull
    BaseUrls baseUrls
) {

    private static final String GITHUB_BASE_URL = "https://api.github.com";
    private static final String STACKOVERFLOW_BASE_URL = "https://api.stackexchange.com";
    private static final String BASE_URL = "http://local.host:8090";

    @Bean
    GitHubWebClient gitHubClient() {
        return new GitHubWebClient(baseUrls.gitHubApi);
    }

    @Bean
    StackOverflowWebClient stackOverflowClient() {
        return new StackOverflowWebClient(STACKOVERFLOW_BASE_URL);
    }

    @Bean
    public BotAPIClient botAPIClient() {
        return new BotAPIClient(BASE_URL);
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record BaseUrls(@NotNull String gitHubApi, @NotNull String stackOverflowApi) {
    }

}
