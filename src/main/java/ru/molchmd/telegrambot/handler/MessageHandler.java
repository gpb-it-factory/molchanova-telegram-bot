package ru.molchmd.telegrambot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.telegrambot.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandler {
    private final Map<String, Command> commands = new HashMap<>();

    public MessageHandler(List<Command> commands) {
        commands.forEach(command -> this.commands.put(command.getName(), command));
    }

    public SendMessage createResponse(Update update) {
        return processCommand(update);
    }

    private SendMessage processCommand(Update update) {
        String message = update.getMessage().getText().toLowerCase();
        Command command = commands.get(message);
        if (command == null) {
            return notFoundCommand(update);
        }
        return command.getMessage(update);
    }

    private SendMessage notFoundCommand(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                """
                Я вас не понимаю!
                Используйте /help для просмотра списка доступных команд.
                """
        );
    }
}
