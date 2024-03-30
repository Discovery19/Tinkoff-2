package edu.java.scrapper.configuration;

import edu.java.scrapper.bot_api.client.BotAPIClient;
import edu.java.scrapper.client.github.GitHubWebClient;
import edu.java.scrapper.client.stackoverflow.StackOverflowWebClient;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import reactor.util.retry.Retry;

@Slf4j
@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(

    @NotNull
    @Bean
    Scheduler scheduler,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    RetryConfig retryConfig
) {

    private static final String GITHUB_BASE_URL = "https://api.github.com";
    private static final String STACKOVERFLOW_BASE_URL = "https://api.stackexchange.com";
    private static final String BASE_URL = "http://localhost:8090";

    @Bean
    GitHubWebClient gitHubClient() {
        return new GitHubWebClient(GITHUB_BASE_URL, retryConfig);
    }

    @Bean
    StackOverflowWebClient stackOverflowClient() {
        return new StackOverflowWebClient(STACKOVERFLOW_BASE_URL, retryConfig);
    }

    @Bean
    public BotAPIClient botAPIClient() {
        return new BotAPIClient(BASE_URL, retryConfig);
    }

    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay,
                            @NotNull Duration linkCheckingFrequency) {
    }

    public enum AccessType {
        JDBC, JPA, JOOQ
    }

    public enum BackOff {
        CONSTANT,
        LINEAR,
        EXPONENTIAL
    }

    public record RetryConfig(BackOff backOff, int attempts, int timeout) {
        private static final String ANSWER = "Retry failed. Performing action. Increasing delay to: ";
        private static final String SECONDS_STR = " seconds.";

        public Retry getRetryConfig() {
            return switch (backOff) {
                case CONSTANT -> Retry.fixedDelay(attempts, Duration.ofSeconds(timeout)).doAfterRetry(context ->
                    log.error("Retry failed. Performing action. Delay: " + timeout + SECONDS_STR));
                case LINEAR -> Retry.backoff(attempts, Duration.ofSeconds(timeout)).doAfterRetry(context -> {
                    long currentDelay = context.totalRetries() * timeout;
                    log.error(
                        ANSWER + currentDelay + SECONDS_STR);
                });
                case EXPONENTIAL -> {
                    log.error(
                        ANSWER + timeout + SECONDS_STR);
                    yield Retry.backoff(attempts, Duration.ofSeconds(timeout)).doAfterRetry(context -> {
                        long currentDelay = (long) Math.pow(timeout, context.totalRetries());

                        log.error(
                            ANSWER + currentDelay + SECONDS_STR);
                    });
                }
            };
        }
    }
}
