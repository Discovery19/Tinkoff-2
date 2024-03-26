package edu.scrapper.api.repositories;

import edu.scrapper.api.repositories.dto.LinkDTO;
import java.net.URI;
import java.util.List;

public interface LinksRepository {
    LinkDTO add(Long chatId, URI url);

    LinkDTO remove(Long chatId, URI url);

    List<LinkDTO> findAll(Long chatId);
}
