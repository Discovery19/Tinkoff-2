package edu.scrapper.api.controllers;

import edu.scrapper.api.service.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScrapperController implements ScrapperInterface {
    private final TgChatService tgChatService;

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return tgChatService.registerChat(id);
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return tgChatService.deleteChat(id);
    }



}
