package edu.java.scrapper.bot_api.client;

import edu.java.scrapper.bot_api.request.BotRequest;
import edu.java.scrapper.bot_api.response.BotResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotAPIClient {
    private final WebClient webClient;
    private final ApplicationConfig.RetryConfig retryConfig;

    public BotAPIClient(WebClient webClient, ApplicationConfig.RetryConfig retryConfig) {
        this.webClient = webClient;
        this.retryConfig = retryConfig;
    }

    public BotAPIClient(String baseUrl, ApplicationConfig.RetryConfig retryConfig) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.retryConfig = retryConfig;
    }

    public ResponseEntity<BotResponse> sendUpdate(BotRequest request) {
        return webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                resp -> Mono.error(new ServiceException(resp.statusCode().toString())))
            .toEntity(BotResponse.class)
            .retryWhen(retryConfig.getRetryConfig())
            .block();
    }

}
