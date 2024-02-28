package edu.java.client;

import edu.java.response.LinkRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotAPIClient {
    private final WebClient webClient;

    public BotAPIClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<Void> sendUpdate(LinkRequest linkRequest) {
        return webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(linkRequest))
            .retrieve()
            .bodyToMono(Void.class);
    }

}
