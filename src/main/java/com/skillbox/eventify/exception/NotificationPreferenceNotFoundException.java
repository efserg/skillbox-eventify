package com.skillbox.eventify.exception;

public class NotificationPreferenceNotFoundException extends NotFoundException {

    private static final String MSG = "Не найдены настройки оповещения пользователя";

    public NotificationPreferenceNotFoundException() {
        super(MSG);
    }

}
