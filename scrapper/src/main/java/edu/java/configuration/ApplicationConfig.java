package edu.java.configuration;

import edu.java.client.gitHub.GitHubWebClient;
import edu.java.client.stackOverflow.StackOverflowWebClient;
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

    @Bean
    GitHubWebClient gitHubClient() {
        return new GitHubWebClient(baseUrls.gitHubApi);
    }

    @Bean
    StackOverflowWebClient stackOverFlowClient() {
        return new StackOverflowWebClient(baseUrls.stackOverflowApi);
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record BaseUrls(@NotNull String gitHubApi, @NotNull String stackOverflowApi) {
    }

}
