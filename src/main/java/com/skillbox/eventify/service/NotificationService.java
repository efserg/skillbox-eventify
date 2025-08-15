package com.skillbox.eventify.service;

import com.skillbox.eventify.model.NotificationPreferences;
import com.skillbox.eventify.model.UserInfo;

public interface NotificationService {

    String linkTelegram(UserInfo user);

    void confirmLink(Long chatId, String code);

    NotificationPreferences getNotificationPreferences(UserInfo user);

    void deleteNotificationPreferences(UserInfo user);

    NotificationPreferences createOrUpdateNotification(NotificationPreferences preferences, UserInfo user);
}
