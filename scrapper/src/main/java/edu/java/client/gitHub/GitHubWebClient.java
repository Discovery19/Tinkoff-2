package edu.java.client.gitHub;

import edu.java.response.RepositoryResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubWebClient implements GitHubClient {
    private static final String BASE_URL = "https://api.github.com/";
    private static final String URI = "/repos/{owner}/{repo}";

    private final WebClient webClient;

    public GitHubWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public GitHubWebClient() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    @Override
    public Mono<RepositoryResponse> fetchRepository(String owner, String repo) {
        return webClient.get()
            .uri(URI, owner, repo)
            .retrieve()
            .bodyToMono(RepositoryResponse.class);
    }

}
