package com.skillbox.eventify.exception;

public class UnauthorizedException extends AuthException {

    private static final String MSG = "Неверный email, пароль или верификационный код";

    public UnauthorizedException() {
        super(MSG);
    }
}
