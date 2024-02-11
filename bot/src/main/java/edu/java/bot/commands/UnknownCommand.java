package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UnknownCommand implements Command{
    @Override
    public String command() {
        return "/unknown";
    }

    @Override
    public String description() {
        return "<b>Unknown command print /help for commands list</b>";
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, description());
    }
}
