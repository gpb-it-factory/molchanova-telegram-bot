package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.RestTemplateMockFactory;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /createaccount")
public class CreateAccountCommandTest {
    private final RestTemplate rest = new RestTemplateMockFactory.RestTemplateCreateAccountCommand();

    @DisplayName("Проверка ответа на создание счета")
    @Test
    void getResponseMessageCreated() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("CREATED", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "Счет успешно создан!";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на уже созданный счет")
    @Test
    void getResponseMessageConflict() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("CONFLICT", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Вы уже создали счет.";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на незарегистрированного пользователя")
    @Test
    void getResponseMessageBadRequest() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("BAD_REQUEST", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Вы не зарегистрированы!\nЧтобы зарегистрироваться, введите /register.";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на недоступность сервера")
    @Test
    void getResponseMessageServiceUnavailable() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("SERVICE_UNAVAILABLE", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "Сервер недоступен!\nПопробуйте позже.";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любое исключение")
    @Test
    void getResponseMessageUnknownException() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("UNKNOWN_EXCEPTION", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любой другой статус ответа")
    @Test
    void getResponseMessageUndefined() {
        CreateAccountCommand createAccountCommand = new CreateAccountCommand("INTERNAL_SERVER_ERROR", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = createAccountCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}
