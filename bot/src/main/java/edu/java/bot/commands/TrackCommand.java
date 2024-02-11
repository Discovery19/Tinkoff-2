package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class TrackCommand implements Command{

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
        return new SendMessage(chatId, "<b>Send tracking link</b>");
    }
}
