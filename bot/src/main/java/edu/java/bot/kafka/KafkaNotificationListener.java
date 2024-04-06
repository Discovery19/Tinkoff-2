package edu.java.bot.kafka;

import edu.java.bot.requests.BotRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaNotificationListener {

    private final NotificationService notificationService;

    public KafkaNotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${app.scrapper-topic}",
                   groupId = "links",
                   containerFactory = "kafkaListenerContainerFactory")
    @RetryableTopic(
        attempts = "1",
        kafkaTemplate = "kafkaTemplate",
        dltStrategy = DltStrategy.FAIL_ON_ERROR,
        dltTopicSuffix = "_dlq")
    public void listen(@Payload BotRequest request) {
        log.info("notification listener");
        log.info(request.description());
        log.info(request.toString());
        log.info(String.valueOf(request.getClass()));
        notificationService.processNotification(request);
    }

    @DltHandler
    public void handleDltPayment(Object botRequest) {
        log.info("Event on dlt " + botRequest);
    }

}
