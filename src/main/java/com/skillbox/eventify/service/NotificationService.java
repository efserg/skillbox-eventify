package com.skillbox.eventify.service;

import org.springframework.transaction.annotation.Transactional;
import com.skillbox.eventify.model.UserInfo;

public interface NotificationService {

    String linkTelegram(UserInfo user);

    @Transactional
    void confirmLink(Long chatId, String code);
}
