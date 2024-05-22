package ru.molchmd.telegrambot.commands;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpCommand implements ICommand {
    private final String responseText;

    public HelpCommand() {
        StringBuilder builder = new StringBuilder("""
                Всегда готов помочь!
                 
                Доступные команды:
                """);
        for (Command command : Command.values())
            if (command.isDisplayToMenu) {
                builder.append(command.name);
                builder.append(" - ");
                builder.append(command.description);
                builder.append("\n");
            }
        responseText = builder.toString();
    }

    @Override
    public SendMessage getResponseMessage(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                responseText
        );
    }

    @Override
    public @NonNull Command getCommand() {
        return Command.HELP;
    }
}
