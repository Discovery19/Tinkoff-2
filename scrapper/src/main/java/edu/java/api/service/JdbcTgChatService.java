package edu.java.api.service;

import edu.java.api.repositories.JdbcChatsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("jdbcChatService")
public class JdbcTgChatService implements TgChatService {
    JdbcChatsRepository jdbcChatsRepository;

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
