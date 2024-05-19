package ru.molchmd.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.molchmd.telegrambot.handler.MessageHandler;

@Component
public class MiniBankTelegramBot extends TelegramLongPollingBot {
    private final String name;
    private final String token;
    private final MessageHandler messageHandler;

    public MiniBankTelegramBot(@Value("${telegrambot.name}") String name,
                               @Value("${telegrambot.token}") String token,
                               MessageHandler messageHandler) {
        super(token);
        this.name = name;
        this.token = token;
        this.messageHandler = messageHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() == false || update.getMessage().hasText() == false) {
            return;
        }

        try {
            execute(messageHandler.createResponse(update));
        }
        catch (TelegramApiException e) {
            System.out.println("! Error [execute]: " + e);
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
