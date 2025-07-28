package com.skillbox.eventify.service.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.skillbox.eventify.service.NotificationService;

@Component
@Transactional(readOnly = true)
public class TelegramBotNotificator extends TelegramLongPollingBot {

    private final NotificationService notificationService;

    @Getter
    private final String botToken;
    @Getter
    private final String botUsername;

    public TelegramBotNotificator(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            @Autowired NotificationService notificationService) {
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.notificationService = notificationService;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.startsWith("/start")) {
                String code = update.getMessage().getText().split("\\s+")[1];
                notificationService.confirmLink(update.getMessage().getChatId(), code);
            }
        }
    }

}