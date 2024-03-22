package edu.java.api.repositories.jpa;

import edu.java.api.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {

}
