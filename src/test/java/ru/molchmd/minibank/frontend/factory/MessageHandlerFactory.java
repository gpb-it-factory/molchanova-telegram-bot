package ru.molchmd.minibank.frontend.factory;

import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.telegrambot.commands.*;
import ru.molchmd.minibank.frontend.telegrambot.handler.MessageHandler;

import java.util.Arrays;
import java.util.List;

public class MessageHandlerFactory {
    public static MessageHandler createMessageHandler() {
        StartCommand startCommand = Mockito.mock(StartCommand.class);
        HelpCommand helpCommand = Mockito.mock(HelpCommand.class);
        PingCommand pingCommand = Mockito.mock(PingCommand.class);
        RegisterCommand registerCommand = Mockito.mock(RegisterCommand.class);
        CreateAccountCommand createAccountCommand = Mockito.mock(CreateAccountCommand.class);
        CurrentBalanceCommand currentBalanceCommand = Mockito.mock(CurrentBalanceCommand.class);

        Mockito.when(startCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "START"));
        Mockito.when(helpCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "HELP"));
        Mockito.when(pingCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "PING"));
        Mockito.when(registerCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "REGISTER"));
        Mockito.when(createAccountCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "CREATE_ACCOUNT"));
        Mockito.when(currentBalanceCommand.getResponseMessage(Mockito.any(Update.class))).thenReturn(new SendMessage("1", "CURRENT_BALANCE"));

        Mockito.when(startCommand.getCommand()).thenReturn(Command.START);
        Mockito.when(helpCommand.getCommand()).thenReturn(Command.HELP);
        Mockito.when(pingCommand.getCommand()).thenReturn(Command.PING);
        Mockito.when(registerCommand.getCommand()).thenReturn(Command.REGISTER);
        Mockito.when(createAccountCommand.getCommand()).thenReturn(Command.CREATE_ACCOUNT);
        Mockito.when(currentBalanceCommand.getCommand()).thenReturn(Command.CURRENT_BALANCE);

        List<ICommand> commands = Arrays.asList(
                startCommand,
                helpCommand,
                pingCommand,
                registerCommand,
                createAccountCommand,
                currentBalanceCommand
        );
        return new MessageHandler(commands);
    }
}
