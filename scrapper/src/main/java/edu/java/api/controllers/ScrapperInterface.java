package edu.java.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface ScrapperInterface {

    @PostMapping("/tg-chat/{id}")
    ResponseEntity<Long> registerChat(@PathVariable Long id);

    @DeleteMapping("/tg-chat/{id}")
    ResponseEntity<Long> deleteChat(@PathVariable Long id);
}
