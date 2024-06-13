package ru.molchmd.minibank.frontend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.molchmd.minibank.frontend.telegrambot.MiniBankTelegramBot;

@Configuration
public class TelegramBotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi(MiniBankTelegramBot bot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);
        return botsApi;
    }
}
