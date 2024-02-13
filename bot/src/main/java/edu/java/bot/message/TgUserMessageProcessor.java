package edu.java.bot.message;
//CHECKSTYLE:OFF: checkstyle:ImportOrder
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UnknownCommand;
import edu.java.bot.commands.UntrackCommand;
import java.util.List;
import edu.java.bot.service.MyDataBase;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//CHECKSTYLE:OFF: checkstyle:MultipleStringLiterals
public class TgUserMessageProcessor implements UserMessageProcessor {
    private String waitingForLink = "";

    @Getter private static final List<? extends Command> COMMANDS = List.of(
        new StartCommand(),
        new HelpCommand(),
        new TrackCommand(),
        new UntrackCommand(),
        new ListCommand(),
        //must be last )))
        new UnknownCommand()
    );

    @Override
    public List<? extends Command> commands() {
        return COMMANDS;
    }

    @Override
    public SendMessage process(Update update) {
        String messageText = update.message().text();
        Long chatId = update.message().chat().id();

        if (waitingForLink.equals("/track") || waitingForLink.equals("/untrack")) {
            return getURL(chatId, messageText);
        } else {
            return defaultProcess(chatId, messageText, update);
        }
    }

    private SendMessage getURL(Long chatId, String messageText) {
        boolean flag;
        StringBuilder sb = new StringBuilder();
        // Ожидаем ссылку после команды /track или /untrack
        if (waitingForLink.equals("/track")) {
            flag = MyDataBase.add(chatId, messageText);
        } else {
            flag = MyDataBase.remove(chatId, messageText);
        }
        if (flag) {
            sb.append("Ссылка успешно ");
            if (waitingForLink.equals("/track")) {
                sb.append("сохранена.");
            } else {
                sb.append("удалена.");
            }
        } else {
            sb.append("Неверная ссылка, проверьте ссылку еще раз и используйте нужную вам команду))");
        }
        waitingForLink = "";
        return new SendMessage(chatId, sb.toString());
    }

    private SendMessage defaultProcess(Long chatId, String messageText, Update update) {
        if (messageText.startsWith("/")) {
            // Обработка команды
            for (Command command : COMMANDS) {
                log.info(command.command());
                if (messageText.equals(command.command())) {
                    if (messageText.equals("/track") || messageText.equals("/untrack")) {
                        // Пользователь вводит ссылку после команды /track или /untrack
                        waitingForLink = messageText;
                        return new SendMessage(chatId, "Пожалуйста, отправьте ссылку.");
                    } else {
                        return command.handle(update);
                    }
                }
            }
        } else {
            return commands().getLast().handle(update);
        }
        return null;
    }
}

