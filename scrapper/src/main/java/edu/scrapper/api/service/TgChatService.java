package edu.java.api.service;

import org.springframework.http.ResponseEntity;

public interface TgChatService {
    ResponseEntity<Long> registerChat(Long chatId);

    ResponseEntity<Long> deleteChat(Long chatId);
}
