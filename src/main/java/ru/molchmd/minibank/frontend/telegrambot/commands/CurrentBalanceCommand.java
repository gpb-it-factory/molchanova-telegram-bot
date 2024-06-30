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
import ru.molchmd.minibank.frontend.client.dto.response.AccountsListResponse;
import ru.molchmd.minibank.frontend.telegrambot.util.TextResponse;

@Component
public class CurrentBalanceCommand implements ICommand {
    private final String endpoint;
    private final RestTemplate rest;
    private final ObjectMapper mapper;

    public CurrentBalanceCommand(@Value("${telegrambot.client.urls.endpoints.accounts.get}") String endpoint,
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

        var response = executeRequest(
                update.getMessage().getChatId()
        );

        message.setText(getTextFromResponse(response));
        message.enableMarkdown(true);
        return message;
    }

    private ResponseEntity<String> executeRequest(Long userId) {
        try {
            ResponseEntity<String> response = rest.getForEntity(
                    endpoint,
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
            case OK -> text = getOkTextResponse(response.getBody());
            case BAD_REQUEST -> text = TextResponse.userIsNotRegistered();
            case SERVICE_UNAVAILABLE -> text = TextResponse.serverIsNotAvailable();
            default -> text = TextResponse.somethingWentWrong();
        }
        return text;
    }

    private String getOkTextResponse(String json) {
        AccountsListResponse[] balanceList;
        try {
            balanceList = mapper.readValue(json, AccountsListResponse[].class);
        } catch (JsonProcessingException e) {
            return TextResponse.somethingWentWrong();
        }

        if (balanceList.length == 0) {
            return "У вас пока нет счетов!\nЧтобы создать счет, введите /createaccount.";
        }

        var text = new StringBuilder("Ваши счета:\n");
        for (var account : balanceList) {
            text.append("```").append(account.getAccountName())
                .append("\nБаланс: ").append(account.getAmount());
            text.append("```\n");
        }
        return text.toString();
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.CURRENT_BALANCE;
    }
}
