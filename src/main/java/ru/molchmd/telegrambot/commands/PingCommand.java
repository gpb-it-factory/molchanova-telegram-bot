package ru.molchmd.telegrambot.commands;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PingCommand implements Command {
    @Override
    public SendMessage getResponseMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("pong");
        return message;
    }

    @Override
    public @NonNull String getName() {
        return "/ping";
    }

    @Override
    public @NonNull String getDescription() {
        return "отвечу pong";
    }

    @Override
    public boolean isDisplayToMenu() {
        return true;
    }
}
