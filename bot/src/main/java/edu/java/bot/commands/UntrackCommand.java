package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.MyDataBase;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command, Tracking {

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
    public String linkOperation(Long chatId, String link) {
        return flagsOperation(MyDataBase.remove(chatId, link));
    }

    @Override
    public String goodAnswer() {
        return "Ссылка успешно удалена!";
    }
}
