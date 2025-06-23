package com.skillbox.eventify.exception;

public class BookingNotFoundException extends NotFoundException {

    private static final String MSG = "Бронирование не найдено: %d";

    public BookingNotFoundException(Long id) {
        super(MSG.formatted(id));
    }

}
