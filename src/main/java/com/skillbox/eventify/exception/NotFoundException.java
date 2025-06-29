package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends RuntimeException implements BusinessException {

    @Override
    public Level getLevel() {
        return Level.ERROR;
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
