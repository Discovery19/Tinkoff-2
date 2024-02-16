package edu.java.bot.message;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TgUserMessageProcessor implements UserMessageProcessor {
    private final List<? extends Command> commands;

    @Autowired
    public TgUserMessageProcessor(Command... commands) {
        this.commands = Arrays.stream(commands).toList();
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        String messageText = update.message().text().trim();
        if (messageText.startsWith("/")) {
            for (Command command : commands) {
                log.info(command.command());
                if (messageText.startsWith(command.command())) {
                    return command.handle(update);
                }
            }
        }
        return commands().stream().filter(command -> command.command().equals("/unknown")).findFirst().orElseThrow()
            .handle(update);
    }
}


