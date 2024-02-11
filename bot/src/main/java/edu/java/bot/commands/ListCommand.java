package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.MyDataBase;

public class ListCommand implements Command{

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
        StringBuilder stringBuilder = new StringBuilder();
        for (String str: MyDataBase.getLinks(chatId)){
            stringBuilder.append(str).append("\n");
        }
        return new SendMessage(chatId, stringBuilder.toString());
    }
}
