package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.requests.BotRequest;
import edu.java.bot.responses.BotResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BotService {
    private final TgBot tgBot;

    public BotService(TgBot tgBot) {
        this.tgBot = tgBot;
    }

    public ResponseEntity<BotResponse> update(BotRequest botRequest) {
        List<Long> usersToWrite = botRequest.tgChatsIds();
        for (Long userId : usersToWrite) {
            tgBot.execute(new SendMessage(userId, botRequest.description() + "\n" + botRequest.link().toString()));
        }
        return ResponseEntity.ok(new BotResponse(botRequest.link(), botRequest.description()));
    }
}
