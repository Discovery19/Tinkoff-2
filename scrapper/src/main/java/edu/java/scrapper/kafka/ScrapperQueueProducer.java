package edu.java.scrapper.kafka;

import edu.java.scrapper.bot_api.request.BotRequest;
import edu.java.scrapper.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueProducer {

    private final KafkaTemplate<String, BotRequest> linkKafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(BotRequest request) {
        log.info(applicationConfig.topic());
        log.info(String.valueOf(request.link()));
        linkKafkaTemplate.send(applicationConfig.topic(), request);
    }

}

