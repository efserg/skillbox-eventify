package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }
}
