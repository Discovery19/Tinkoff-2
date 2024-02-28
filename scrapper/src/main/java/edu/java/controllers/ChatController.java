package edu.java.controllers;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import edu.java.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ChatController implements Chat {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    public Mono<Long> registerChat(@PathVariable Long id) {
        log.info("контроллер словил");
        return chatService.registerChat(id);
    }

    public Mono<Long> deleteChat(@PathVariable Long id) {
        return chatService.deleteChat(id);
    }

    public Mono<AllLinksDTO> getLinks(@PathVariable Long id) {
        return chatService.getLinks(id);

    }

    public Mono<Long> trackLink(@RequestBody RequestDTO dto) {
        return chatService.trackLink(dto);
    }

    public Mono<Long> untrackLink(@RequestBody RequestDTO dto) {
        return chatService.untrackLink(dto);
    }
}
