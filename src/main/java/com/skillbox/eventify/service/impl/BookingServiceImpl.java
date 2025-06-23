package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.BookingNotFoundException;
import com.skillbox.eventify.schema.Booking.Fields;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.service.BookingService;
import jakarta.persistence.criteria.Predicate;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.skillbox.eventify.mapper.BookingMapper;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.repository.BookingRepository;
import com.skillbox.eventify.schema.Booking;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Page<BookingResponse> findAll(Long eventId, Boolean unconfirmedOnly, Pageable pageable) {
        Specification<Booking> spec = buildSpecification(eventId, unconfirmedOnly);
        final Page<Booking> bookings = bookingRepository.findAll(spec, pageable);
        return bookingMapper.entityToResponse(bookings);
    }

    @Override
    public Integer calculateBookedTickets(Long eventId) {
        return bookingRepository.bookedCount(eventId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        bookingRepository.delete(booking);
    }

    @Override
    @Transactional
    public void confirm(Long id) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        booking.setConfirmed(true);
    }

    private Specification<Booking> buildSpecification(Long eventId, Boolean unconfirmedOnly) {
        return (root, query, cb) -> {
            final Builder<Predicate> builder = Stream.builder();
            if (eventId != null) {
                builder.add(cb.equal(root.get(Fields.event).get(Event.Fields.id), eventId));
            }
            if (Boolean.TRUE.equals(unconfirmedOnly)) {
                builder.add(cb.equal(root.get(Fields.confirmed), false));
            }
            return cb.and(builder.build().toArray(Predicate[]::new));
        };
    }
}
