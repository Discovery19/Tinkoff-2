package edu.java.configuration;

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

    @Bean
    public WebClient githubWebClient() {
        return WebClient.builder().baseUrl(GITHUB_BASE_URL).build();
    }

    @Bean
    public WebClient stackOverflowWebClient() {
        return WebClient.builder().baseUrl(STACKOVERFLOW_BASE_URL).build();
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

}
