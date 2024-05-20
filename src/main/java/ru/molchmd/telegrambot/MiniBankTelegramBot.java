package ru.molchmd.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.DeleteMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.molchmd.telegrambot.commands.Command;
import ru.molchmd.telegrambot.commands.ICommand;
import ru.molchmd.telegrambot.handler.MessageHandler;

import java.util.ArrayList;
import java.util.List;

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

        createMenu(true);
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

    private void createMenu(boolean isCreate) {
        if (!isCreate) {
            try {
                execute(new DeleteMyCommands(new BotCommandScopeDefault(), "ru"));
                log.info("Menu was deleted successfully");
            }
            catch (TelegramApiException e) {
                log.error("Menu was not deleted");
            }
            return;
        }

        List<BotCommand> menu = new ArrayList<>();
        for (Command command : Command.values()) {
            if (command.isDisplayToMenu)
                menu.add(new BotCommand(command.name, command.description));
        }

        try {
            execute(new SetMyCommands(menu, new BotCommandScopeDefault(), "ru"));
            log.info("Menu was created successfully, command count - {}", menu.size());
        }
        catch (TelegramApiException e) {
            log.error("Menu was not created");
        }
    }
}
