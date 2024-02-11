package edu.java.bot.message;

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

@Slf4j
public class TgUserMessageProcessor implements UserMessageProcessor {
    private String waitingForLink = "";

    @Getter private static final List<? extends Command> commands = List.of(
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
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        String messageText = update.message().text();
        Long chatId = update.message().chat().id();


        if (waitingForLink.equals("/track") || waitingForLink.equals("/untrack")) {
            boolean flag;
                // Ожидаем ссылку после команды /track или /untrack
                if (waitingForLink.equals("/track")) {
                    flag = MyDataBase.add(chatId, messageText);
                    if (flag){
                        return new SendMessage(chatId, "Ссылка успешно сохранена.");
                    }
                } else {
                    flag = MyDataBase.remove(chatId, messageText);
                    if (flag){
                        return new SendMessage(chatId, "Ссылка успешно удалена.");
                    }
                }
                waitingForLink = "";
                return new SendMessage(chatId, "Неверная ссылка, проверьте ссылку еще раз и используйте нужную вам команду))");
        } else {
            if (messageText.startsWith("/")) {
                // Обработка команды
                for (Command command : commands) {
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
        }
        return null;
    }
}

