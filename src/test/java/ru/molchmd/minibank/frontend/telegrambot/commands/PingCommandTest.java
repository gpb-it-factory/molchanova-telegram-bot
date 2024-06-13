package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /ping")
public class PingCommandTest {
    private final ICommand pingCommand = new PingCommand();
    private final Update update = UpdateFactory.createUpdate();
    private final String EXPECTED_ANSWER = "pong";

    @DisplayName("Проверка ответа")
    @Test
    void getResponseMessage() {
        Assertions.assertEquals(EXPECTED_ANSWER, pingCommand.getResponseMessage(update).getText());
    }
}
