package edu.java.client.gitHub;

import edu.java.response.RepositoryResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<RepositoryResponse> fetchRepository(String owner, String repo);
}
