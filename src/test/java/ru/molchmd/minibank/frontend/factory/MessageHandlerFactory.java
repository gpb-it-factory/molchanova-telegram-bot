package ru.molchmd.minibank.frontend.factory;

import ru.molchmd.minibank.frontend.telegrambot.commands.*;
import ru.molchmd.minibank.frontend.telegrambot.handler.MessageHandler;

import java.util.Arrays;
import java.util.List;

public class MessageHandlerFactory {
    public static MessageHandler createMessageHandler() {
        List<ICommand> commands = Arrays.asList(
                new StartCommand(),
                new HelpCommand(),
                new PingCommand(),
                new RegisterCommand("CREATED", new RestTemplateMockFactory.RestTemplateRegisterCommand()),
                new CreateAccountCommand("CREATED", new RestTemplateMockFactory.RestTemplateCreateAccountCommand())
        );
        return new MessageHandler(commands);
    }
}
