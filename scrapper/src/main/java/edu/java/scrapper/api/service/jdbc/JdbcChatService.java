package edu.java.scrapper.api.service.jdbc;

import edu.java.scrapper.api.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.api.service.TgChatService;
import org.springframework.http.ResponseEntity;

public class JdbcChatService implements TgChatService {
    private final JdbcChatRepository jdbcChatsRepository;

    public JdbcChatService(JdbcChatRepository tg) {
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
