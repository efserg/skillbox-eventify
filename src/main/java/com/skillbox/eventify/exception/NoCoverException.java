package com.skillbox.eventify.exception;

public class NoCoverException extends ValidateException {

    public static final String MSG = "Файл обложки не предоставлен";

    public NoCoverException() {
        super(MSG);
    }
}
