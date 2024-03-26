package edu.java.bot.controllers;

import edu.java.bot.requests.BotRequest;
import edu.java.bot.responses.BotResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BotInterface {
    @PostMapping(value = "/updates", produces = "application/json")
    ResponseEntity<BotResponse> updateLink(@RequestBody BotRequest request);

}
