package ru.molchmd.telegrambot.commands;

import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    SendMessage getResponseMessage(Update update);
    @NonNull String getName();
    @NonNull String getDescription();
    boolean isDisplayToMenu();
}
