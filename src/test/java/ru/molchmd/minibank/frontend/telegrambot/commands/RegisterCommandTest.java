package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.RestTemplateMockFactory;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /register")
public class RegisterCommandTest {
    private final RestTemplate rest = new RestTemplateMockFactory.RestTemplateRegisterCommand();

    @DisplayName("Проверка ответа на создание пользователя")
    @Test
    void getResponseMessageCreated() {
        RegisterCommand registerCommand = new RegisterCommand("CREATED", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "Вы успешно зарегистрировались!";
        String responseMessage = registerCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на уже зарегистрированного пользователя")
    @Test
    void getResponseMessageConflict() {
        RegisterCommand registerCommand = new RegisterCommand("CONFLICT", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Вы уже зарегистрированы!";
        String responseMessage = registerCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на недоступность сервера")
    @Test
    void getResponseMessageServiceUnavailable() {
        RegisterCommand registerCommand = new RegisterCommand("SERVICE_UNAVAILABLE", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "Сервер недоступен!\nПопробуйте позже.";
        String responseMessage = registerCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любое исключение")
    @Test
    void getResponseMessageUnknownException() {
        RegisterCommand registerCommand = new RegisterCommand("UNKNOWN_EXCEPTION", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = registerCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любой другой статус ответа")
    @Test
    void getResponseMessageBadRequest() {
        RegisterCommand registerCommand = new RegisterCommand("INTERNAL_SERVER_ERROR", rest);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = registerCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}
