package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class UntrackCommand implements Command{

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
        return new SendMessage(chatId, "<b>Submit the link you want to untrack</b>");
    }
}
