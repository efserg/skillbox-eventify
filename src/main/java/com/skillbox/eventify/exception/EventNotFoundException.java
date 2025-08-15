package com.skillbox.eventify.exception;

public class EventNotFoundException extends NotFoundException {

    private static final String MSG = "Мероприятие не найдено: %d";

    public EventNotFoundException(Long eventId) {
        super(MSG.formatted(eventId));
    }
}
