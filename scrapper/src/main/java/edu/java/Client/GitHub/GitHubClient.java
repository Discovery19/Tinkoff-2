package edu.java.Client.GitHub;

import edu.java.Response.RepositoryResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<RepositoryResponse> fetchRepository(String owner, String repo);
}
