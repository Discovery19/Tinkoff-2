package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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
        log.error("user id: " + update.message().chat().id());
        long chatId = update.message().chat().id();
        return new SendMessage(chatId, "<b>Welcome to the bot!</b>");
    }
}

