package ru.molchmd.telegrambot.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements Command {
    @Override
    public SendMessage getMessage(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                """
                Всегда готов помочь!
                 
                Доступные команды:
                /help - узнать список всех команд
                /ping - отвечу pong
                """
        );
    }

    @Override
    public String getName() {
        return "/help";
    }
}
