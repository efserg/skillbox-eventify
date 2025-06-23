package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public abstract class ValidateException extends RuntimeException implements BusinessException {

    @Override
    public Level getLevel() {
        return Level.ERROR;
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.BAD_REQUEST;
    }

    public ValidateException() {
    }

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
