package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public class BookingForbiddenException extends AuthException {

    private static final String MSG = "Изменение или удаление чужого бронирования запрещено";

    public BookingForbiddenException() {
        super(MSG);
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public Level getLevel() {
        return Level.WARNING;
    }
}
