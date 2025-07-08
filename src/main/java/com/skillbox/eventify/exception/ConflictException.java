package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RuntimeException implements BusinessException {

    public ConflictException(String message) {
        super(message);
    }

    @Override
    public Level getLevel() {
        return Level.ERROR;
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getCode() {
        return "CONFLICT_ERROR";
    }
}
