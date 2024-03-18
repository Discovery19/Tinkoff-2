package edu.java.api.controllers;

import edu.java.api.service.TgChatService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
public class ScrapperController implements ScrapperInterface {

    private final TgChatService tgChatService;

    public ScrapperController(@Qualifier("jooqChatService") TgChatService tgChatService) {
        this.tgChatService = tgChatService;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return tgChatService.registerChat(id);
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return tgChatService.deleteChat(id);
    }

}
