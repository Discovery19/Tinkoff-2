package edu.java.api.repositories;

import edu.java.api.repositories.dto.DefaultAnswerDTO;
import java.net.URI;

public interface LinksRepository {
    DefaultAnswerDTO add(Long chatId, URI url);
    DefaultAnswerDTO remove(Long chatId, URI url);
    void findAll(Long chatId);}
