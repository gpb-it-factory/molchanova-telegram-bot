package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.client.dto.request.CreateUserRequest;

@Component
public class RegisterCommand implements ICommand {
    private final String endpoint;
    private final RestTemplate rest;

    public RegisterCommand(@Value("${telegrambot.client.urls.endpoints.users.register}") String endpoint,
                           RestTemplate rest) {
        this.endpoint = endpoint;
        this.rest = rest;
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        var response = executeRequest(update.getMessage().getChatId());

        message.setText(getTextFromResponse(response));
        return message;
    }

    private ResponseEntity<String> executeRequest(Long userId) {
        try {
            ResponseEntity<String> response = rest.postForEntity(
                    endpoint,
                    new CreateUserRequest(userId),
                    String.class
            );
            return response;
        }
        catch (ResourceAccessException exception) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String getTextFromResponse(ResponseEntity<String> response) {
        String text;
        HttpStatus code = HttpStatus.valueOf(response.getStatusCode().value());

        switch (code) {
            case CREATED -> text = "Вы успешно зарегистрировались!";
            case CONFLICT -> text = "Ошибка! Вы уже зарегистрированы!";
            case SERVICE_UNAVAILABLE -> text = "Сервер недоступен!\nПопробуйте позже.";
            default -> text = "Ошибка! Что-то пошло не так.";
        }
        return text;
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.REGISTER;
    }
}
