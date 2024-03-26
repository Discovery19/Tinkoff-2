package edu.java.bot.client;

import edu.java.bot.responses.LinkResponse;
import edu.java.bot.responses.ListLinkResponse;
import java.net.URI;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class ScrapperAPIClient {
    private static final String TG_CHAT_URI = "/tg-chat/{id}";
    private static final String LINKS_URI = "/links";

    private final WebClient webClient;

    public ScrapperAPIClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ScrapperAPIClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<Long> registerChat(Long chatId) {
        return webClient.post()
            .uri(TG_CHAT_URI, chatId)
            .retrieve()
            .toEntity(Long.class)
            .block();
    }

    public ResponseEntity<Long> deleteChat(Long chatId) {
        return webClient.delete()
            .uri(TG_CHAT_URI, chatId)
            .retrieve()
            .toEntity(Long.class)
            .block();
    }

    public ResponseEntity<LinkResponse> trackLink(Long chatId, URI link) {
        return webClient.post()
            .uri(TG_CHAT_URI + LINKS_URI, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(link))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

    public ResponseEntity<ListLinkResponse> getLinks(Long chatId) {
        return webClient.get()
            .uri(TG_CHAT_URI + LINKS_URI, chatId)
            .retrieve()
            .toEntity(ListLinkResponse.class)
            .block();
    }

    public ResponseEntity<LinkResponse> deleteLink(Long chatId, URI link) {
        return webClient.method(HttpMethod.DELETE)
            .uri(TG_CHAT_URI + LINKS_URI, chatId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(link))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

}
