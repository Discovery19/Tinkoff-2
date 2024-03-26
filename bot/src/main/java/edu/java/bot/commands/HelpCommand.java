package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private final List<? extends Command> commands;

    @Autowired
    public HelpCommand(Command... commands) {
        this.commands = Arrays.stream(commands)
            .filter(command -> !command.command()
                .equals("/unknown")).toList();
    }

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
        for (Command command : commands) {
            if (!command.command().isEmpty()) {
                stringBuilder.append("<b>").append(command.command()).append("</b>").append("\t")
                    .append(command.description()).append("\n");
            }
        }
        return new SendMessage(chatId, stringBuilder.toString());
    }

}
