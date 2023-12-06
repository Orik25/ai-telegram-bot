package com.orik.botapi.config;


import com.orik.botapi.service.impl.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {
    private TelegramBot bot;
    private boolean isRegistered=false;
    @Autowired
    public BotInitializer(TelegramBot bot) {
        this.bot = bot;
    }


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        if (!isRegistered) {
            try {
                telegramBotsApi.registerBot(bot);
                isRegistered = true;
                bot.setRegistered(true);
            } catch (TelegramApiException e) {
                log.error("Error occurred: " + e.getMessage());
            }
        }
    }

    private boolean isBotRegistered() {
        return bot.isRegistered();
    }

}