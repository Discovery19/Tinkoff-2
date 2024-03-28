package edu.java.scrapper.api.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import edu.java.scrapper.api.service.TgChatService;
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
