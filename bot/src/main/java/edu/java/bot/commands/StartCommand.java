package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand implements Command {

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Start the bot";
    }

    @Override
    public SendMessage handle(Update update) {
        // Реализация обработки команды /start
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "<b>Welcome to the bot!</b>");
    }
}

