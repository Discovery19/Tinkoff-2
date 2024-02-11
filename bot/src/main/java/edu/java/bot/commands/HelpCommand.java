package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.TgUserMessageProcessor;
import lombok.SneakyThrows;
import java.util.List;

public class HelpCommand implements Command {
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Help commands";
    }

    @SneakyThrows @Override
    public SendMessage handle(Update update) {
        long chatId = update.message().chat().id();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>List of commands:</b>").append("\n");
        List<? extends Command> list = TgUserMessageProcessor.getCommands();
        for (Command command : list) {
            if (!command.command().isEmpty()) {
                stringBuilder.append("<b>").append(command.command()).append("</b>").append("\t").append(command.description()).append("\n");
            }
        }
        return new SendMessage(chatId, stringBuilder.toString());
    }
}
