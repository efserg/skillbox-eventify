package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public class WrongDateException extends ValidateException {

    public static final String MSG = "Ошибка валидации даты: %s";

    public WrongDateException(String message) {
        super(MSG.formatted(message));
    }

}
