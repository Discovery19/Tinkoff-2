package edu.java.bot.Controllers;

import edu.java.bot.requests.BotRequest;
import edu.java.bot.responses.BotResponse;
import edu.java.bot.service.BotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController implements BotInterface {
    BotService botService;

    public BotController(BotService service) {
        this.botService = service;
    }

    @Override
    public ResponseEntity<BotResponse> updateLink(BotRequest request) {
        return botService.update(request);
    }
}
