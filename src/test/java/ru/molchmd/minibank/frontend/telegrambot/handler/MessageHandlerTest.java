package ru.molchmd.minibank.frontend.telegrambot.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.MessageHandlerFactory;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка выбора команды через ответ")
public class MessageHandlerTest {
    private final MessageHandler messageHandler = MessageHandlerFactory.createMessageHandler();

    @DisplayName("Проверка ответа команды /start")
    @Test
    void getResponseOnStartCommand() {
        Update update = UpdateFactory.createUpdate("/start");
        String EXPECTED_ANSWER = """
                        Добро пожаловать в наш мини-банк, Tester!
                        Я ваш персональный банковский помощник.
                        
                        Используйте /help, чтобы узнать список доступных команд.
                        """;

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа команды /help")
    @Test
    void getResponseOnHelpCommand() {
        Update update = UpdateFactory.createUpdate("/help");
        String EXPECTED_ANSWER = """
            Всегда готов помочь!
                 
            Доступные команды:
            | /help - список доступных команд
            | /register - зарегистрироваться
            | /createaccount - создать счет
            """;

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа команды /ping")
    @Test
    void getResponseOnPingCommand() {
        Update update = UpdateFactory.createUpdate("/ping");
        String EXPECTED_ANSWER = "pong";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа неизвестной команды")
    @Test
    void getResponseOnUnknownCommand() {
        Update update = UpdateFactory.createUpdate("/hello");
        String EXPECTED_ANSWER = """
                Я вас не понимаю!
                Используйте /help для просмотра списка доступных команд.
                """;

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа команды /register")
    @Test
    void getResponseOnRegisterCommand() {
        Update update = UpdateFactory.createUpdate("/register");
        String EXPECTED_ANSWER = "Вы успешно зарегистрировались!";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа команды /createaccount")
    @Test
    void getResponseOnCreateAccountCommand() {
        Update update = UpdateFactory.createUpdate("/createaccount");
        String EXPECTED_ANSWER = "Счет успешно создан!";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}