package edu.java.configuration;

import edu.java.client.BotAPIClient;
import edu.java.client.github.GitHubWebClient;
import edu.java.client.stackoverflow.StackOverflowWebClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    @Bean
    Scheduler scheduler
) {

    private static final String GITHUB_BASE_URL = "https://api.github.com";
    private static final String STACKOVERFLOW_BASE_URL = "https://api.stackexchange.com";
    private static final String BASE_URL = "http://local.host:8090";

    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder().baseUrl(GITHUB_BASE_URL).build();
    }

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

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

}
