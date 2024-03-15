//import com.pengrad.telegrambot.model.Chat;
//import com.pengrad.telegrambot.model.Message;
//import com.pengrad.telegrambot.model.Update;
//import com.pengrad.telegrambot.request.SendMessage;
//import edu.java.bot.commands.Command;
//import edu.java.bot.commands.HelpCommand;
//import edu.java.bot.commands.ListCommand;
//import edu.java.bot.commands.StartCommand;
//import edu.java.bot.commands.TrackCommand;
//import edu.java.bot.commands.UnknownCommand;
//import edu.java.bot.commands.UntrackCommand;
//import edu.java.bot.message.TgUserMessageProcessor;
//import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.util.ReflectionTestUtils;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//class BotTest {
//    private TgUserMessageProcessor messageProcessor;
//    private Chat chat;
//    private Update update;
//    private Message message;
//    @BeforeEach
//    public void setUp() {
//        messageProcessor = new TgUserMessageProcessor();
//        ReflectionTestUtils.setField(messageProcessor, "commands",
//            List.of(new StartCommand(), new HelpCommand(), new TrackCommand(), new UntrackCommand(), new ListCommand(), new UnknownCommand()));
//        chat = mock(Chat.class);
//        update = mock(Update.class);
//        message = mock(Message.class);
//    }
//
//    @Test
//    void testStartCommand() {
//        //arrange
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/start");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Welcome to the bot!"));
//    }
//
//    @Test
//    void testUnknownCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/unknown");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Unknown command print /help for commands list"));
//    }
//    @Test
//    void testListEmptyCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/list");
//        when(message.chat()).thenReturn(chat);
//        when(chat.id()).thenReturn(123L);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Вы не добавили ни одной ссылки(("));
//    }
//    @Test
//    void testListCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/list");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("https://edu.tinkoff.ru/my-activities/courses/stream/b37f2c9a-b73c-4cc8-a092-0bcbf49faac7/exam/18328/1"));
//    }
//    @Test
//    void testTrackGoodLinkCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/track https://edu.tinkoff.ru/my-activities/courses/stream/b37f2c9a-b73c-4cc8-a092-0bcbf49faac7/exam/18328/1");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Ссылка успешно сохранена!"));
//    }
//    @Test
//    void testTrackGoodNextLinkCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn(" /track     https://edu.tinkoff.ru/my-activities/courses/stream/b37f2c9a-b73c-4cc8-a092-0bcbf49faac7/exam/18328/1");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Ссылка успешно сохранена!"));
//    }
//    @Test
//    void testTrackBadLinkCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/track a-b73c-4cc8-a092-0bcbf49faac7/exam/18328/1");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Неверная ссылка, проверьте ссылку еще раз и используйте нужную вам команду))"));
//    }
//    @Test
//    void testTrackBadNextLinkCommandMessage() {
//        //arrange
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn("/track   ");
//        when(message.chat()).thenReturn(chat);
//        //act
//        SendMessage response = messageProcessor.process(update);
//        //assert
//        Assertions.assertNotNull(response);
//        Assertions.assertTrue(response.toWebhookResponse().contains("Неверная ссылка, проверьте ссылку еще раз и используйте нужную вам команду))"));
//    }
//}
