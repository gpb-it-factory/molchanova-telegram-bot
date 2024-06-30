package ru.molchmd.minibank.frontend.telegrambot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.RestTemplateMockFactory;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /currentbalance")
public class CurrentBalanceCommandTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private String jsonResponse;

    @DisplayName("Проверка ответа на возвращение счета")
    @Test
    void getResponseMessageAccountList() {
        jsonResponse = "[{\"accountName\": \"Акционный\",\n\"amount\": \"5000.0\"}]";
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = """
                Ваши счета:
                ```Акционный
                Баланс: 5000.0```
                """;
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на возвращение пустого списка счетов")
    @Test
    void getResponseMessageEmptyAccountList() {
        jsonResponse = "[]";
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "У вас пока нет счетов!\nЧтобы создать счет, введите /createaccount.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на незарегистрированного пользователя")
    @Test
    void getResponseMessageNotExistUser() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("BAD_REQUEST", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Вы не зарегистрированы!\nЧтобы зарегистрироваться, введите /register.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на недоступность сервера")
    @Test
    void getResponseMessageServiceUnavailable() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("SERVICE_UNAVAILABLE", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "Сервер недоступен!\nПопробуйте позже.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любое исключение")
    @Test
    void getResponseMessageUnknownException() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("UNKNOWN_EXCEPTION", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любой другой статус ответа")
    @Test
    void getResponseMessageUndefined() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("INTERNAL_SERVER_ERROR", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на неуспешный парсинг json")
    @Test
    void getResponseMessageFailJson() {
        jsonResponse = "[{\"accountName\": \"Акционный\",\n\"balance\": \"5000.0\"}]";
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateCurrentBalanceCommand(jsonResponse);
        CurrentBalanceCommand currentBalanceCommand = new CurrentBalanceCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate();

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = currentBalanceCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}
