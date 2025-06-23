package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public interface BusinessException {

    public enum Level {
        INFO,
        WARNING,
        ERROR
    }

    Level getLevel();
    HttpStatus getResponseCode();

}
