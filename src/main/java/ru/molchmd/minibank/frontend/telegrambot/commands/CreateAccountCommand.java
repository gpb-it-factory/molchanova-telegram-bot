package ru.molchmd.minibank.frontend.telegrambot.commands;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.client.dto.request.CreateAccountRequest;
import ru.molchmd.minibank.frontend.telegrambot.util.TextResponse;

@Component
public class CreateAccountCommand implements ICommand {
    private final String endpoint;
    private final RestTemplate rest;
    private final String accountName;

    public CreateAccountCommand(@Value("${telegrambot.client.urls.endpoints.accounts.create}") String endpoint,
                                RestTemplate rest) {
        this.endpoint = endpoint;
        this.rest = rest;
        this.accountName = "Акционный";
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        var response = executeRequest(
                update.getMessage().getChatId(),
                accountName
        );

        message.setText(getTextFromResponse(response));
        message.enableMarkdown(true);
        return message;
    }

    private ResponseEntity<String> executeRequest(Long userId, String accountName) {
        try {
            ResponseEntity<String> response = rest.postForEntity(
                    endpoint,
                    new CreateAccountRequest(accountName),
                    String.class,
                    userId
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

    private String getTextFromResponse(ResponseEntity<String> response) {
        String text;
        HttpStatus code = HttpStatus.valueOf(response.getStatusCode().value());

        switch (code) {
            case CREATED -> text = "Счет успешно создан!";
            case BAD_REQUEST -> text = TextResponse.userIsNotRegistered();
            case CONFLICT -> text = "_Ошибка!_ Вы уже создали счет.";
            case SERVICE_UNAVAILABLE -> text = TextResponse.serverIsNotAvailable();
            default -> text = TextResponse.somethingWentWrong();
        }
        return text;
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.CREATE_ACCOUNT;
    }
}
