package ru.molchmd.minibank.frontend.telegrambot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.client.dto.request.CreateTransferRequest;
import ru.molchmd.minibank.frontend.client.dto.response.Error;
import ru.molchmd.minibank.frontend.telegrambot.util.ExtendedStatus;
import ru.molchmd.minibank.frontend.telegrambot.util.TextResponse;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class TransferCommand implements ICommand {
    private final String endpoint;
    private final RestTemplate rest;
    private final ObjectMapper mapper;

    public TransferCommand(@Value("${telegrambot.client.urls.endpoints.transfers.create}") String endpoint,
                           RestTemplate rest,
                           ObjectMapper mapper) {
        this.endpoint = endpoint;
        this.rest = rest;
        this.mapper = mapper;
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        String[] words = update.getMessage().getText().split("\\s+");
        if (words.length != 3) {
            return getSendMessageWithText(message, getInfoMessage());
        }
        String toUserName = words[1];
        String amount = words[2];

        if (toUserName.equals(update.getMessage().getChat().getUserName())) {
            return getSendMessageWithText(message, "_Ошибка!_ Нельзя переводить самому себе.");
        }
        Optional<String> errorText = getErrorTextFromCheckAmount(amount);
        if (errorText.isPresent()) {
            return getSendMessageWithText(message, errorText.get());
        }

        var response = executeRequest(
                update.getMessage().getChat().getUserName(),
                toUserName,
                amount
        );

        return getSendMessageWithText(message, getTextFromResponse(response, toUserName));
    }

    private ResponseEntity<String> executeRequest(String from, String to, String amount) {
        try {
            ResponseEntity<String> response = rest.postForEntity(
                    endpoint,
                    new CreateTransferRequest(from, to, amount),
                    String.class
            );
            return response;
        }
        catch (ResourceAccessException exception) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getTextFromResponse(ResponseEntity<String> response, String toUserName) {
        String text;
        HttpStatus code = HttpStatus.valueOf(response.getStatusCode().value());

        switch (code) {
            case OK -> text = "Сумма успешно переведена пользователю `" + toUserName + "`!";
            case BAD_REQUEST -> text = getBadRequestTextResponse(response.getBody(), toUserName);
            case SERVICE_UNAVAILABLE -> text = TextResponse.serverIsNotAvailable();
            default -> text = TextResponse.somethingWentWrong();
        }
        return text;
    }

    private String getBadRequestTextResponse(String json, String toUserName) {
        ExtendedStatus extendedStatus;
        try {
            extendedStatus = mapper.readValue(json, Error.class).getType();
        } catch (JsonProcessingException e) {
            return TextResponse.somethingWentWrong();
        }

        String text;
        switch (extendedStatus) {
            case SENDER_RECIPIENT_SAME -> text = "_Ошибка!_ Нельзя переводить самому себе.";
            case AMOUNT_CONVERT_NUMBER -> text = "_Ошибка!_ Введите верную сумму.\nПример: 100.5.";
            case AMOUNT_MORE_TWO_CHAR -> text = "_Ошибка!_ После точки может стоять не более 2-х цифр.\nПример: 100.25.";
            case AMOUNT_LESS_ZERO -> text = "_Ошибка!_ Сумма должна быть больше нуля.";
            case AMOUNT_LESS_ALLOWED_MIN -> text = "_Ошибка!_ Сумма слишком маленькая для перевода.";
            case AMOUNT_HIGHER_ALLOWED_MAX -> text = "_Ошибка!_ Сумма слишком большая для перевода.";
            case SENDER_USER_NOT_EXIST -> text = TextResponse.userIsNotRegistered();
            case SENDER_USER_ACCOUNT_NOT_EXIST -> text = "_Ошибка!_ У вас пока нет счетов!\nЧтобы создать счет, введите /createaccount.";
            case AMOUNT_NOT_ENOUGH -> text = "_Ошибка!_ На балансе счета не хватает средств для совершения перевода.";
            case RECIPIENT_USER_NOT_EXIST -> text = "_Ошибка!_ Пользователь `" + toUserName + "` не зарегистрирован.";
            case RECIPIENT_USER_ACCOUNT_NOT_EXIST -> text = "_Ошибка!_ У пользователя `" + toUserName + "` нет счета.";
            default -> text = TextResponse.somethingWentWrong();
        }
        return text;
    }

    private SendMessage getSendMessageWithText(SendMessage message, String text) {
        message.setText(text);
        message.enableMarkdown(true);
        return message;
    }

    private Optional<String> getErrorTextFromCheckAmount(String amount) {
        BigDecimal transferAmount;
        try {
            transferAmount = new BigDecimal(amount).stripTrailingZeros();
            if (transferAmount.scale() > 2) {
                return Optional.of("_Ошибка!_ После точки может стоять не более 2-х цифр.\nПример: 100.25.");
            }
        }
        catch (NumberFormatException exception) {
            return Optional.of("_Ошибка!_ Введите верную сумму.\nПример: 100.5.");
        }
        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return Optional.of("_Ошибка!_ Сумма должна быть больше нуля.");
        }
        return Optional.empty();
    }

    private String getInfoMessage() {
        return "Чтобы перевести сумму на счет другого пользователя, введите:\n" +
                "```\n/transfer [userName] [amount]```\n" +
                "где `[userName]` - имя пользователя без @, " +
                "`[amount]` - сумма перевода.\n" +
                "\nШаблон: `/transfer name 100`";
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.TRANSFER;
    }
}
