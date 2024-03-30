package edu.java.scrapper.client.stackoverflow;

import edu.java.scrapper.api.response.client_response.QuestionResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowWebClient implements StackOverflowClient {

    private final WebClient webClient;
    private final ApplicationConfig.RetryConfig retryConfig;

    public StackOverflowWebClient(String url, ApplicationConfig.RetryConfig retryConfig) {
        this.webClient = WebClient.builder().baseUrl(url).build();
        this.retryConfig = retryConfig;
    }

    public StackOverflowWebClient(WebClient stackOverflowWebClient, ApplicationConfig.RetryConfig retryConfig) {
        this.webClient = stackOverflowWebClient;
        this.retryConfig = retryConfig;
    }

    @Override
    public Mono<QuestionResponse> fetchQuestion(long questionId) {
        return webClient.get()
            .uri("/2.3/questions/{questionId}?order=desc&sort=activity&site=stackoverflow", questionId)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                resp -> Mono.error(new ServiceException(resp.statusCode().toString())))
            .bodyToMono(QuestionResponse.class)
            .retryWhen(retryConfig.getRetryConfig());
    }
}
