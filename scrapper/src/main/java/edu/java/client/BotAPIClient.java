package edu.java.client;

import edu.java.requests.BotRequest;
import edu.java.response.BotResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotAPIClient {
    private final WebClient webClient;

    public BotAPIClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public BotAPIClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ResponseEntity<BotResponse> sendUpdate(BotRequest request) {
        return webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .toEntity(BotResponse.class).block();
    }

}
