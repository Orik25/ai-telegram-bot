package com.orik.botapi.config;


import com.orik.botapi.service.impl.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BotInitializer {
    private TelegramBot bot;

    @Autowired
    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }

    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            TelegramBotsApi telegramBotsApi = null;
            try {
                telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            try {
                telegramBotsApi.registerBot(bot);
                log.info("Telegram bot registered successfully.");
            } catch (TelegramApiException e) {
                log.error("Error occurred while registering the Telegram bot: " + e.getMessage());
            }
        }).join(); // Зачекайте завершення асинхронної реєстрації
    }

}