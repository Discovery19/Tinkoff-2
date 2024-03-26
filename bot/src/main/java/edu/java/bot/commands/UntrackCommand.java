package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperAPIClient;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command, Tracking {
    private final ScrapperAPIClient scrapperAPIClient;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Untrack URL";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, parseTracking(chatId, update.message().text().trim()));
    }

    @Override
    public String linkOperation(Long chatId, URI link) {
        scrapperAPIClient.deleteLink(chatId, link);
        return goodAnswer();
    }

    @Override
    public String goodAnswer() {
        return "Ссылка успешно удалена!";
    }
}
