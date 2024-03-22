package edu.java.api.service.jpa;

import edu.java.api.exceptions.ResourceNotFoundException;
import edu.java.api.model.Chat;
import edu.java.api.repositories.jpa.JpaChatRepository;
import edu.java.api.service.TgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class JpaChatService implements TgChatService {
    private final JpaChatRepository chatRepository;

    @Override
    public ResponseEntity<Long> registerChat(Long chatId) {
        var chat = chatRepository.findById(chatId).orElseGet(
            () -> {
                var c = new Chat();
                c.setId(chatId);
                chatRepository.saveAndFlush(c);
                return c;
            }
        );
        return ResponseEntity.ok(chat.getId());
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long chatId) {
        var chat = chatRepository.findById(chatId).orElseThrow(ResourceNotFoundException::new);
        chatRepository.delete(chat);
        chatRepository.flush();
        return ResponseEntity.ok(chat.getId());
    }
}
