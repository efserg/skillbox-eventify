package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.BookingForbiddenException;
import com.skillbox.eventify.exception.BookingNotFoundException;
import com.skillbox.eventify.exception.ConflictException;
import com.skillbox.eventify.exception.EventNotFoundException;
import com.skillbox.eventify.exception.NumberValidateException;
import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.UpdateBookingRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.EventRepository;
import com.skillbox.eventify.schema.Booking.Fields;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.service.BookingService;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
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

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Page<BookingResponse> findAll(Long eventId, Boolean unconfirmedOnly, Pageable pageable) {
        Specification<Booking> spec = buildSpecification(eventId, unconfirmedOnly);
        final Page<Booking> bookings = bookingRepository.findAll(spec, pageable);
        return bookingMapper.entityToResponse(bookings);
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

    @Override
    public List<BookingResponse> findByUserId(Long userId) {
        final List<Booking> bookings = bookingRepository.findAllByUser_Id(userId);
        return bookingMapper.entityToResponse(bookings);
    }

    @Override
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request, UserInfo user) {
        validateTicketCount(request.getTicketCount());

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new EventNotFoundException(request.getEventId()));

        checkTicketAvailability(event, request.getTicketCount());

        Booking booking = bookingMapper.requestToEntity(request, event, user);
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.entityToResponse(savedBooking);
    }

    @Override
    public BookingResponse getById(Long id, UserInfo user) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BookingForbiddenException();
        }
        return bookingMapper.entityToResponse(booking);
    }

    @Override
    public void cancelBooking(Long id, UserInfo user) {
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BookingForbiddenException();
        }
        bookingRepository.delete(booking);
    }

    @Override
    public BookingResponse update(Long id, @Valid UpdateBookingRequest request, UserInfo user) {
        validateTicketCount(request.getTicketCount());
        final Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
        if (!booking.getUser().getId().equals(user.getId())) {
            throw new BookingForbiddenException();
        }
        final Integer requestTicketCount = request.getTicketCount();
        final Integer ticketCount = booking.getTicketCount();
        if (!Objects.equals(requestTicketCount, ticketCount)) {
            final Event event = booking.getEvent();
            final Integer bookedCount = bookingRepository.bookedCount(event.getId());
            final Integer totalTickets = event.getTotalTickets();
            int newBookedCount = bookedCount - ticketCount + requestTicketCount;
            if (newBookedCount > totalTickets) {
                int availableTickets = totalTickets - (bookedCount - ticketCount);
                throw new NumberValidateException("Для бронирования доступно %s билетов".formatted(availableTickets));
            }
            booking.setTicketCount(requestTicketCount);
            final Booking saved = bookingRepository.save(booking);
            return bookingMapper.entityToResponse(saved);
        }
        return bookingMapper.entityToResponse(booking);
    }

    private void validateTicketCount(Integer ticketCount ) {
        if (ticketCount < 1) {
            throw new NumberValidateException("Количество билетов должно быть положительным");
        }
    }

    private void checkTicketAvailability(Event event, int requestedTickets) {
        int availableTickets = event.getTotalTickets() - bookingRepository.bookedCount(event.getId());
        if (requestedTickets > availableTickets) {
            throw new ConflictException("Запрошено больше билетов, чем доступно. Доступно: " + availableTickets);
        }
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
