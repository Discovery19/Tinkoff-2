package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperAPIClient;
import edu.java.bot.responses.LinkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperAPIClient scrapperAPIClient;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "All your URLs";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        var links = scrapperAPIClient.getLinks(chatId).getBody().links();
        StringBuilder stringBuilder = new StringBuilder();
        for (LinkResponse str : links) {
            stringBuilder.append(str.link()).append("\n");
        }
        return new SendMessage(chatId, stringBuilder.toString());
    }
}
