package edu.java.scrapper.api.repositories.jpa;

import edu.java.scrapper.api.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {

}
