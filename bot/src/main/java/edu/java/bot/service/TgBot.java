package edu.java.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.message.TgUserMessageProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TgBot implements Bot {

    private final TelegramBot bot;
    private final TgUserMessageProcessor messageProcessor;

    @Autowired
    public TgBot(ApplicationConfig applicationConfig, TgUserMessageProcessor messageProcessor) {
        this.bot = new TelegramBot(applicationConfig.telegramToken());
        this.messageProcessor = messageProcessor;
        createMenu();
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            SendMessage response = messageProcessor.process(update);
            if (response != null) {
                bot.execute(response.parseMode(ParseMode.HTML));
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            } else {
                log.error(e.getMessage());
            }
        });
    }

    public void createMenu() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        for (Command command : messageProcessor.commands()) {
            if (!command.command().equals("/unknown")) {
                listOfCommands.add(command.toApiCommand());
            }
        }
        BotCommand[] commandsArray = listOfCommands.toArray(new BotCommand[0]);
        this.execute(new SetMyCommands(commandsArray));
    }

    @Override
    public void close() {
        bot.shutdown();
    }
}


