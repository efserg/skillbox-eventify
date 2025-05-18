package com.skillbox.eventify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.skillbox.eventify.mapper.BookingMapper;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.repository.BookingRepository;
import com.skillbox.eventify.schema.Booking;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Page<BookingResponse> findAll(Pageable pageable) {
        final Page<Booking> bookings = bookingRepository.findAll(pageable);
        return  bookingMapper.map(bookings);
    }
}
