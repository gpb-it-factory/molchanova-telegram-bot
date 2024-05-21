package ru.molchmd.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.molchmd.telegrambot.handler.MessageHandler;

@Component
@Slf4j
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
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        try {
            execute(messageHandler.createResponse(update));
            log.info("Send message to id[{}]", update.getMessage().getChatId());
        }
        catch (TelegramApiException e) {
            log.error("Message was not sent to id[{}]", update.getMessage().getChatId());
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }
}
