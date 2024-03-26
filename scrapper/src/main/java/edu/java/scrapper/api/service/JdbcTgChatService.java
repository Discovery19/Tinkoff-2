package edu.scrapper.api.service;

import edu.scrapper.api.repositories.jdbc.JdbcChatsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JdbcTgChatService implements TgChatService {
    private final JdbcChatsRepository jdbcChatsRepository;

    public JdbcTgChatService(JdbcChatsRepository tg) {
        this.jdbcChatsRepository = tg;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return ResponseEntity.ok().body(jdbcChatsRepository.add(id).id());
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return ResponseEntity.ok().body(jdbcChatsRepository.remove(id).id());
    }

}
