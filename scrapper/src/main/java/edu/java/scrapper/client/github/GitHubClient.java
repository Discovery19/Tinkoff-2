package edu.java.scrapper.client.github;

import edu.java.scrapper.api.response.client_response.RepositoryResponse;
import reactor.core.publisher.Mono;

public interface GitHubClient {
    Mono<RepositoryResponse> fetchRepository(String owner, String repo);
}
