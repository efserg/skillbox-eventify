package com.skillbox.eventify.exception;

import org.springframework.http.HttpStatus;

public class FileTooLargeException extends ValidateException {

    public static final String MSG = "Размер файла превышает 5MB";

    public FileTooLargeException() {
        super(MSG);
    }

    @Override
    public HttpStatus getResponseCode() {
        return HttpStatus.PAYLOAD_TOO_LARGE;
    }
}
