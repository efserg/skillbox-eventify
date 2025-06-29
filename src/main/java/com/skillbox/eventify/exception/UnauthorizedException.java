package com.skillbox.eventify.exception;

public class UnauthorizedException extends AuthException {

    private static final String MSG = "Неверный email или пароль";

    public UnauthorizedException() {
        super(MSG);
    }
}
