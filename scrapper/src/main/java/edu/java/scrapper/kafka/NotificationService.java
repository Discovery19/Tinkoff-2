package edu.java.scrapper.kafka;

import edu.java.scrapper.bot_api.client.BotAPIClient;
import edu.java.scrapper.bot_api.request.BotRequest;
import edu.java.scrapper.configuration.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    private final ApplicationConfig applicationConfig;
    private final ScrapperQueueProducer queueProducer;
    private final BotAPIClient botClient;

    public NotificationService(
        ApplicationConfig applicationConfig,
        ScrapperQueueProducer queueProducer,
        BotAPIClient botClient
    ) {
        this.applicationConfig = applicationConfig;
        this.queueProducer = queueProducer;
        this.botClient = botClient;
    }

    public void sendNotification(BotRequest notification) {
        log.info("notification service");
        if (applicationConfig.useQueue()) {
            log.info("use queue");
            queueProducer.sendMessage(notification);
        } else {
            log.info("fuck shit");
            botClient.sendUpdate(notification);
        }
    }
}
