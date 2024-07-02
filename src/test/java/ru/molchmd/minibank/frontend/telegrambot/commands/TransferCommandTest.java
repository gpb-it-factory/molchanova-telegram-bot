package ru.molchmd.minibank.frontend.telegrambot.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.RestTemplateMockFactory;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /transfer")
public class TransferCommandTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private String jsonResponse;

    @DisplayName("Проверка ответа на успешный перевод")
    @Test
    void getResponseMessageTransferOk() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 200");

        String EXPECTED_ANSWER = "Сумма успешно переведена пользователю `cat`!";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на перевод самому себе")
    @Test
    void getResponseMessageTransferMyself() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer tester 200");

        String EXPECTED_ANSWER = "_Ошибка!_ Нельзя переводить самому себе.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на несоответствие количества аргументов в команде")
    @Test
    void getResponseMessageTransferWrongArguments() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer");

        String EXPECTED_ANSWER = "Чтобы перевести сумму на счет другого пользователя, введите:\n" +
                "```\n/transfer [userName] [amount]```\n" +
                "где `[userName]` - имя пользователя без @, " +
                "`[amount]` - сумма перевода.\n" +
                "\nШаблон: `/transfer name 100`";
        
        String responseMessage = transferCommand.getResponseMessage(update).getText();
        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);

        update = UpdateFactory.createUpdate("/transfer cat");
        responseMessage = transferCommand.getResponseMessage(update).getText();
        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);

        update = UpdateFactory.createUpdate("/transfer cat to cat");
        responseMessage = transferCommand.getResponseMessage(update).getText();
        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на неверную сумму: нельзя преобразовать в число")
    @Test
    void getResponseMessageWrongAmount() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat a2s");

        String EXPECTED_ANSWER = "_Ошибка!_ Введите верную сумму.\nПример: 100.5.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на неверную сумму: неверное число копеек")
    @Test
    void getResponseMessageWrongAmountAfterDot() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100.098765");

        String EXPECTED_ANSWER = "_Ошибка!_ После точки может стоять не более 2-х цифр.\nПример: 100.25.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на отрицательную сумму")
    @Test
    void getResponseMessageNegativeAmount() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("OK", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat -100");

        String EXPECTED_ANSWER = "_Ошибка!_ Сумма должна быть больше нуля.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на ошибку: недостаточно средств")
    @Test
    void getResponseMessageAmountNotEnough() {
        jsonResponse = "{\"type\": \"AMOUNT_NOT_ENOUGH\"}";
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("BAD_REQUEST", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100");

        String EXPECTED_ANSWER = "_Ошибка!_ На балансе счета не хватает средств для совершения перевода.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на ошибку: ошибка парсинга json")
    @Test
    void getResponseMessageAmountFailJson() {
        jsonResponse = "{\"type\": \"CAT_TO_CAT\"}";
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("BAD_REQUEST", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100");

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на недоступность сервера")
    @Test
    void getResponseMessageServiceUnavailable() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("SERVICE_UNAVAILABLE", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100");

        String EXPECTED_ANSWER = "Сервер недоступен!\nПопробуйте позже.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любое исключение")
    @Test
    void getResponseMessageUnknownException() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("UNKNOWN_EXCEPTION", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100");

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }

    @DisplayName("Проверка ответа на любой другой статус ответа")
    @Test
    void getResponseMessageUndefined() {
        RestTemplate rest = new RestTemplateMockFactory.RestTemplateTransferCommand(jsonResponse);
        TransferCommand transferCommand = new TransferCommand("INTERNAL_SERVER_ERROR", rest, mapper);
        Update update = UpdateFactory.createUpdate("/transfer cat 100");

        String EXPECTED_ANSWER = "_Ошибка!_ Что-то пошло не так.";
        String responseMessage = transferCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}
