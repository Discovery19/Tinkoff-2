package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperAPIClient;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command, Tracking {
    private final ScrapperAPIClient scrapperAPIClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "track URL";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, parseTracking(chatId, update.message().text().trim()));
    }

    @Override
    public String linkOperation(Long chatId, URI link) {
        if (!link.toString().isBlank()) {
            scrapperAPIClient.trackLink(chatId, link);
        }
        return goodAnswer();
    }

    @Override
    public String goodAnswer() {
        return "Ссылка успешно сохранена!";
    }
}
