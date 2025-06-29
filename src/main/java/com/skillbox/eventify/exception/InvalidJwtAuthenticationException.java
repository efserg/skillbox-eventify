package com.skillbox.eventify.exception;

public class InvalidJwtAuthenticationException extends AuthException {
    private static final String MSG = "Неверный токен авторизации";


    public InvalidJwtAuthenticationException() {
        super(MSG);
    }
}
