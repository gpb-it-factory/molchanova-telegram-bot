package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /start")
public class StartCommandTest {
    private final ICommand startCommand = new StartCommand();
    private final Update update = UpdateFactory.createUpdate();
    private final String EXPECTED_ANSWER = """
                        Добро пожаловать в наш мини-банк, Tester!
                        Я ваш персональный банковский помощник.
                        
                        Используйте /help, чтобы узнать список доступных команд.
                        """;

    @DisplayName("Проверка ответа")
    @Test
    void getResponseMessage() {
        Assertions.assertEquals(EXPECTED_ANSWER, startCommand.getResponseMessage(update).getText());
    }
}
