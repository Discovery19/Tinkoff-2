package edu.scrapper.client.github;

import edu.scrapper.api.response.client_response.RepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubWebClient implements GitHubClient {

    private final WebClient webClient;

    public GitHubWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public GitHubWebClient(WebClient githubWebClient) {
        this.webClient = githubWebClient;
    }

    @Override
    public Mono<RepositoryResponse> fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .bodyToMono(RepositoryResponse.class);
    }

}
