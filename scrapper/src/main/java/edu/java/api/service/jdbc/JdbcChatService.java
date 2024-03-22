package edu.java.api.service.jdbc;

import edu.java.api.repositories.jdbc.JdbcChatRepository;
import edu.java.api.service.TgChatService;
import org.springframework.http.ResponseEntity;


public class JdbcChatService implements TgChatService {
    JdbcChatRepository jdbcChatRepository;

    public JdbcChatService(JdbcChatRepository tg) {
        this.jdbcChatRepository = tg;
    }

    @Override
    public ResponseEntity<Long> registerChat(Long id) {
        return ResponseEntity.ok().body(jdbcChatRepository.add(id).id());
    }

    @Override
    public ResponseEntity<Long> deleteChat(Long id) {
        return ResponseEntity.ok().body(jdbcChatRepository.remove(id).id());
    }

}
