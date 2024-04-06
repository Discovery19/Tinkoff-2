package edu.java.bot.kafka;

import edu.java.bot.requests.BotRequest;
import edu.java.bot.service.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final BotService botService;

    public void processNotification(BotRequest request) {
        botService.update(request);
    }
}
