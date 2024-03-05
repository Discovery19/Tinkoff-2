package edu.java.client.stackoverflow;

import edu.java.response.QuestionResponse;
import reactor.core.publisher.Mono;

public interface StackOverflowClient {
    Mono<QuestionResponse> fetchQuestion(long questionId);
}
