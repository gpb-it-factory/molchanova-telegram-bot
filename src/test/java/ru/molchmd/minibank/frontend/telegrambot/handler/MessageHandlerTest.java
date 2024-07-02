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

    @DisplayName("Проверка выбора команды /start")
    @Test
    void getResponseOnStartCommand() {
        Update update = UpdateFactory.createUpdate("/start");
        String EXPECTED_ANSWER = "START";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора команды /help")
    @Test
    void getResponseOnHelpCommand() {
        Update update = UpdateFactory.createUpdate("/help");
        String EXPECTED_ANSWER = "HELP";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора команды /ping")
    @Test
    void getResponseOnPingCommand() {
        Update update = UpdateFactory.createUpdate("/ping");
        String EXPECTED_ANSWER = "PING";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора неизвестной команды")
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

    @DisplayName("Проверка выбора команды /register")
    @Test
    void getResponseOnRegisterCommand() {
        Update update = UpdateFactory.createUpdate("/register");
        String EXPECTED_ANSWER = "REGISTER";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора команды /createaccount")
    @Test
    void getResponseOnCreateAccountCommand() {
        Update update = UpdateFactory.createUpdate("/createaccount");
        String EXPECTED_ANSWER = "CREATE_ACCOUNT";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора команды /currentbalance")
    @Test
    void getResponseOnCurrentBalanceCommand() {
        Update update = UpdateFactory.createUpdate("/currentbalance");
        String EXPECTED_ANSWER = "CURRENT_BALANCE";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка выбора команды /transfer")
    @Test
    void getResponseOnTransferCommand() {
        Update update = UpdateFactory.createUpdate("/transfer");
        String EXPECTED_ANSWER = "TRANSFER";

        String responseMessage = messageHandler.createResponse(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}