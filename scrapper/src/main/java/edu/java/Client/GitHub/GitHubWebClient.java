package edu.java.Client.GitHub;

import edu.java.Response.RepositoryResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
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
