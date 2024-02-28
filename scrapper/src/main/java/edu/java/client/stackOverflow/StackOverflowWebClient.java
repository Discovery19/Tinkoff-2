package edu.java.client.stackOverflow;

import edu.java.response.QuestionResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowWebClient implements StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public StackOverflowWebClient(WebClient stackOverflowWebClient) {
        this.webClient = stackOverflowWebClient;
    }

    @Override
    public Mono<QuestionResponse> fetchQuestion(long questionId) {
        return webClient.get()
            .uri("/2.3/questions/{questionId}?order=desc&sort=activity&site=stackoverflow", questionId)
            .retrieve()
            .bodyToMono(QuestionResponse.class);
    }
}
