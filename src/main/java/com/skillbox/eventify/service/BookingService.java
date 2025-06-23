package com.skillbox.eventify.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skillbox.eventify.model.BookingResponse;

public interface BookingService {
    Page<BookingResponse> findAll(Long eventId, Boolean unconfirmedOnly, Pageable pageable);

    Integer calculateBookedTickets(Long eventId);

    void delete(Long id);

    void confirm(Long id);
}
