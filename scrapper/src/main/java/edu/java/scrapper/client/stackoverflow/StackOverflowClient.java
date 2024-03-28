package edu.java.scrapper.client.stackoverflow;

import edu.java.scrapper.api.response.client_response.QuestionResponse;
import reactor.core.publisher.Mono;

public interface StackOverflowClient {
    Mono<QuestionResponse> fetchQuestion(long questionId);
}
