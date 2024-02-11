import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.message.TgUserMessageProcessor;
import edu.java.bot.service.TgBot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotTest {
    private TgBot bot;
    private TgUserMessageProcessor messageProcessor;

    @BeforeEach
    public void setUp() {
        bot = mock(TgBot.class);
        messageProcessor = new TgUserMessageProcessor();
    }

    @Test
    void testStartCommand() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        SendMessage response = messageProcessor.process(update);
        System.out.println(response.toWebhookResponse());
        Assertions.assertTrue(response.toWebhookResponse().contains("Welcome to the bot!"));
    }

    @Test
    void testUnknownCommandMessage() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/unknown");
        when(message.chat()).thenReturn(chat);
        SendMessage response = messageProcessor.process(update);
        Assertions.assertTrue(response.toWebhookResponse().contains("Unknown command print /help for commands list"));
    }

}
