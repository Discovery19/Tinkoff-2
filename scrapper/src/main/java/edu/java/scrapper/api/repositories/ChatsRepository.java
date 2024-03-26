package edu.java.scrapper.api.repositories;

import edu.java.scrapper.api.repositories.dto.ChatDTO;
import java.util.List;

public interface ChatsRepository {
    ChatDTO add(Long id);

    ChatDTO remove(Long id);

    List<ChatDTO> findAll();
}
