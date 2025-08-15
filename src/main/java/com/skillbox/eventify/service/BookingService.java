package com.skillbox.eventify.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.UpdateBookingRequest;
import com.skillbox.eventify.model.UserInfo;

public interface BookingService {
    Page<BookingResponse> findAll(Long eventId, Boolean unconfirmedOnly, Pageable pageable);

    void delete(Long id);

    void confirm(Long id);

    List<BookingResponse> findByUserId(Long id);

    BookingResponse createBooking(CreateBookingRequest request, UserInfo user);

    BookingResponse getById(Long id, UserInfo user);

    void cancelBooking(Long id, UserInfo user);

    BookingResponse update(Long id, UpdateBookingRequest request, UserInfo user);
}
