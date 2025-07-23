package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.ConflictException;
import com.skillbox.eventify.exception.EventNotFoundException;
import com.skillbox.eventify.exception.FileStorageException;
import com.skillbox.eventify.exception.FileTooLargeException;
import com.skillbox.eventify.exception.NoCoverException;
import com.skillbox.eventify.exception.NumberValidateException;
import com.skillbox.eventify.exception.WrongDateException;
import com.skillbox.eventify.exception.WrongFileException;
import com.skillbox.eventify.mapper.EventMapper;
import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.EventUpdateRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.BookingRepository;
import com.skillbox.eventify.repository.EventRepository;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.Event.Fields;
import com.skillbox.eventify.service.EventService;
import com.skillbox.eventify.service.FileStorageService;
import jakarta.persistence.criteria.Predicate;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final FileStorageService fileStorageService;
    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventResponse createEvent(EventCreateRequest request, UserInfo user) {
        if (request.getDateTime().isBefore(Instant.now())) {
            throw new WrongDateException("Дата мероприятия не может быть в прошлом");
        }
        Event event = eventMapper.requestToEntity(request, user);
        try {
            // Сохраняем оригинальный URL изображения
            event.setCoverPath(request.getCoverUrl());
            Event savedEvent = eventRepository.save(event);
            log.info("Создано новое мероприятие ID: {}", savedEvent.getId());
            return eventMapper.entityToResponse(savedEvent);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Ошибка при создании мероприятия: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        try {
            bookingRepository.deleteByEventId(eventId);
            eventRepository.delete(event);

            log.info("Мероприятие {} и все связанные бронирования удалены администратором", eventId);

        } catch (DataAccessException e) {
            throw new ConflictException("Ошибка при удалении связанных данных: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public EventResponse uploadEventCover(Long eventId, MultipartFile coverFile) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        try {
            String storedFileName = fileStorageService.storeFile(eventId.toString(),
                    coverFile
            );

            event.setCoverPath(storedFileName);
            Event savedEvent = eventRepository.save(event);

            return eventMapper.entityToResponse(savedEvent);

        } catch (FileStorageException e) {
            log.error("Ошибка при удалении файлов мероприятия {}: {}", eventId, e.getMessage());
            throw new ConflictException("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteEventCover(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getCoverPath() == null || event.getCoverPath().isEmpty()) {
            throw new NoCoverException();
        }

        event.setCoverPath(null);
        eventRepository.save(event);
    }

    @Override
    public Page<EventResponse> getEvents(OffsetDateTime from, OffsetDateTime to, Pageable pageable) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new WrongDateException("Дата 'от' не может быть позже даты 'до'");
        }

        Instant fromInstant = from != null ? from.toInstant() : null;
        Instant toInstant = to != null ? to.toInstant() : null;

        Specification<Event> spec = buildSpecification(fromInstant, toInstant);

        Page<Event> events = eventRepository.findAll(spec, pageable);

        return eventMapper.toPageResponse(events);
    }

    @Override
    public EventResponse getEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return eventMapper.entityToResponse(event);

    }

    @Override
    @Transactional
    public EventResponse update(Long id, EventUpdateRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        final String title = request.getTitle();
        final Instant dateTime = request.getDateTime();
        final String description = request.getDescription();
        final Integer totalTickets = request.getTotalTickets();
        final String coverUrl = request.getCoverUrl();

        if (title != null && !Objects.equals(title, event.getTitle())) {
            event.setTitle(title);
        }
        if (dateTime != null && !Objects.equals(dateTime, event.getDateTime())) {
            event.setDateTime(dateTime);
        }
        if (!Objects.equals(description, event.getDescription())) {
            event.setDescription(description);
        }
        if (totalTickets != null && !Objects.equals(totalTickets, event.getTotalTickets())) {
            final Integer booked = bookingRepository.bookedCount(id);
            if (booked > totalTickets) {
                throw new NumberValidateException(
                        "На мероприятие уже забронировано %s билетов, удалите брони или увеличьте количество билетов".formatted(
                                booked));
            }
            event.setTotalTickets(totalTickets);
        }
        if (coverUrl == null) {
            event.setCoverPath(null);
        } else {
            event.setCoverPath(coverUrl);
        }
        return eventMapper.entityToResponse(event);
    }

    private Specification<Event> buildSpecification(Instant from,
                                                    Instant to) {
        return (root, query, cb) -> {
            final Builder<Predicate> builder = Stream.builder();
            if (from != null) {
                builder.add(cb.greaterThanOrEqualTo(root.get(Fields.dateTime), from));
            }

            if (to != null) {
                builder.add(cb.lessThanOrEqualTo(root.get(Fields.dateTime), to));
            }
            return cb.and(builder.build().toArray(Predicate[]::new));
        };
    }
}
