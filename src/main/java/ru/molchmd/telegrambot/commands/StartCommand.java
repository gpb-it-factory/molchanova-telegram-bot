package ru.molchmd.telegrambot.commands;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements ICommand {
    @Override
    public SendMessage getResponseMessage(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                String.format("""
                        Добро пожаловать в наш мини-банк, %s!
                        Я ваш персональный банковский помощник.
                        
                        Используйте /help, чтобы узнать список доступных команд.
                        """,
                        update.getMessage().getChat().getFirstName()
                )
        );
    }

    @Override
    public @NonNull String getName() {
        return Command.START.name;
    }
}
