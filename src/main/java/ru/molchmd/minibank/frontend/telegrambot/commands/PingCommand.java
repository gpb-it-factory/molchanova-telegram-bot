package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class PingCommand implements ICommand {
    @Override
    public SendMessage getResponseMessage(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText("pong");
        return message;
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.PING;
    }
}
