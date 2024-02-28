package edu.java.client;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
//CHECKSTYLE:OFF: checkstyle:MultipleStringLiterals
public class ScrapperAPIClient {
    private final WebClient webClient;

    public ScrapperAPIClient() {
        log.info("скрапер создан");
        this.webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/tg-chat")
            .build();
    }

    public Mono<Long> registerChat(Long id) {
        log.info("регистрация пошла");
        return webClient.post()
            .uri("/{id}", id)
            .retrieve()
            .bodyToMono(Long.class);
    }

    public Mono<Long> deleteChat(Long id) {
        return webClient.delete()
            .uri("/{id}", id)
            .retrieve()
            .bodyToMono(Long.class);
    }

    public Mono<AllLinksDTO> getLinks(Long id) {
        return webClient.get()
            .uri("/{id}/links", id)
            .retrieve()
            .bodyToMono(AllLinksDTO.class);
    }

    public Mono<Long> trackLink(RequestDTO dto) {
        return webClient.post()
            .uri("/{id}/links", dto.id())
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(Long.class);
    }

    public Mono<Long> untrackLink(RequestDTO dto) {
        return webClient.method(HttpMethod.DELETE)
            .uri("/{id}/links", dto.id())
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(Long.class);
    }
}

