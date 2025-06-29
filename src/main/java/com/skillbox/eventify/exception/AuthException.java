package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public abstract class AuthException extends RuntimeException implements BusinessException {
    @Override
    public Level getLevel() {
        return Level.ERROR;
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.UNAUTHORIZED;
    }

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    protected AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
