package com.skillbox.eventify.service;

import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.UserInfo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skillbox.eventify.model.BookingResponse;

public interface BookingService {
    Page<BookingResponse> findAll(Long eventId, Boolean unconfirmedOnly, Pageable pageable);

    Integer calculateBookedTickets(Long eventId);

    void delete(Long id);

    void confirm(Long id);

    List<BookingResponse> findByUserId(Long id);

    BookingResponse createBooking(CreateBookingRequest request, UserInfo user);

    BookingResponse getById(Long id, UserInfo user);

    void cancelBooking(Long id, UserInfo user);
}
