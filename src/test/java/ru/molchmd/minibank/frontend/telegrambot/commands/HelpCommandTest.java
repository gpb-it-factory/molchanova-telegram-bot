package ru.molchmd.minibank.frontend.telegrambot.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.molchmd.minibank.frontend.factory.UpdateFactory;

@DisplayName("Проверка команды /help")
public class HelpCommandTest {
    private final ICommand helpCommand = new HelpCommand();
    private final Update update = UpdateFactory.createUpdate();
    private final String EXPECTED_ANSWER = """
            Всегда готов помочь!
                 
            Доступные команды:
            | /help - список доступных команд
            | /register - зарегистрироваться
            | /createaccount - создать счет
            | /currentbalance - посмотреть счета
            | /transfer - перевести деньги
            """;

    @DisplayName("Проверка ответа")
    @Test
    void getResponseMessage() {
        String responseMessage = helpCommand.getResponseMessage(update).getText();

        Assertions.assertEquals(EXPECTED_ANSWER, responseMessage);
    }
}
