package edu.java.client.stackOverflow;

import edu.java.response.QuestionResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowWebClient implements StackOverflowClient {

    private final WebClient webClient;
    private static final String BASE_URL = "https://api.stackexchange.com/";
    private static final String URI = "/2.3/questions/{questionId}?order=desc&sort=activity&site=stackoverflow";

    public StackOverflowWebClient(String url) {
        this.webClient = WebClient.builder().baseUrl(url).build();
    }

    public StackOverflowWebClient() {
        this.webClient = WebClient.builder().baseUrl(BASE_URL).build();
    }

    @Override
    public Mono<QuestionResponse> fetchQuestion(long questionId) {
        return webClient.get()
            .uri(URI, questionId)
            .retrieve()
            .bodyToMono(QuestionResponse.class);
    }
}
