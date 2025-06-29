package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public interface BusinessException {

    enum Level {
        INFO,
        WARNING,
        ERROR
    }

    Level getLevel();
    HttpStatus getResponseCode();

}
