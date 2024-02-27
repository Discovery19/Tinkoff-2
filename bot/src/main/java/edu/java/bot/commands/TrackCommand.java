package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.MyDataBase;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command, Tracking {

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
    public String linkOperation(Long chatId, String link) {
        return flagsOperation(MyDataBase.add(chatId, link));
    }



    @Override
    public String goodAnswer() {
        return "Ссылка успешно сохранена!";
    }
}
