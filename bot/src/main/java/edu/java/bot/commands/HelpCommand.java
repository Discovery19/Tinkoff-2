package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.TgUserMessageProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private ApplicationContext applicationContext;


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
        List<? extends Command> list = TgUserMessageProcessor.getCOMMANDS()
            .stream().filter(command -> !command.command().equals("/unknown")).toList();
        for (Command command : list) {
            if (!command.command().isEmpty()) {
                stringBuilder.append("<b>").append(command.command()).append("</b>").append("\t")
                    .append(command.description()).append("\n");
            }
        }
        return new SendMessage(chatId, stringBuilder.toString());
    }

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//        this.list = this.applicationContext.getBean(TgUserMessageProcessor.class).getCOMMANDS();
//    }
}
