package edu.java.scrapper.api.repositories.jpa;

import edu.java.scrapper.api.model.Link;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(String url);
}
