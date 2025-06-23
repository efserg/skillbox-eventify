package com.skillbox.eventify.exception;

public class WrongFileException extends ValidateException {

    public static final String MSG = "Допустимы только JPEG/PNG изображения";

    public WrongFileException() {
        super(MSG);
    }
}
