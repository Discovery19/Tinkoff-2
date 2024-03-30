package edu.java.scrapper.client.github;

import edu.java.scrapper.api.response.client_response.RepositoryResponse;
import edu.java.scrapper.configuration.ApplicationConfig.RetryConfig;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubWebClient implements GitHubClient {

    private final WebClient webClient;
    private final RetryConfig retryConfig;

    public GitHubWebClient(String url, RetryConfig retryConfig) {
        this.webClient = WebClient.builder().baseUrl(url).build();
        this.retryConfig = retryConfig;
    }

    public GitHubWebClient(WebClient githubWebClient, RetryConfig retryConfig) {
        this.webClient = githubWebClient;
        this.retryConfig = retryConfig;
    }

    @Override
    public Mono<RepositoryResponse> fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                resp -> Mono.error(new ServiceException(resp.statusCode().toString()))
            )
            .bodyToMono(RepositoryResponse.class)
            .retryWhen(retryConfig.getRetryConfig());
    }

}
