package com.skillbox.eventify.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skillbox.eventify.model.BookingResponse;

public interface BookingService {
    Page<BookingResponse> findAll(Pageable pageable);
}
