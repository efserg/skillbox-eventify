package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.ConflictException;
import com.skillbox.eventify.exception.EventNotFoundException;
import com.skillbox.eventify.exception.FileStorageException;
import com.skillbox.eventify.exception.FileTooLargeException;
import com.skillbox.eventify.exception.NoCoverException;
import com.skillbox.eventify.exception.WrongDateException;
import com.skillbox.eventify.exception.WrongFileException;
import com.skillbox.eventify.mapper.EventMapper;
import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.repository.BookingRepository;
import com.skillbox.eventify.repository.EventRepository;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.service.EventService;
import com.skillbox.eventify.service.FileStorageService;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
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

    private static final String EVENTS_COVERS_PATH = "events/covers";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
            "image/jpeg",
            "image/png"
    );

    @Override
    @Transactional
    public EventResponse createEvent(EventCreateRequest request, UserDetails user) {
        if (request.getDateTime().isBefore(OffsetDateTime.now())) {
            throw new WrongDateException("Дата мероприятия не может быть в прошлом");
        }
        Event event = eventMapper.requestToEntity(request, user);
        try {
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

            if (event.getCoverPath() != null && !event.getCoverPath().isEmpty()) {
                fileStorageService.deleteFile(EVENTS_COVERS_PATH, event.getCoverPath());
            }

            eventRepository.delete(event);

            log.info("Мероприятие {} и все связанные бронирования удалены администратором", eventId);

        } catch (DataAccessException e) {
            throw new ConflictException("Ошибка при удалении связанных данных: " + e.getMessage());
        } catch (FileStorageException e) {
            log.error("Ошибка при удалении файлов мероприятия {}: {}", eventId, e.getMessage());
            throw new ConflictException("Ошибка при удалении файлов мероприятия");
        }
    }

    @Override
    @Transactional
    public EventResponse uploadEventCover(Long eventId, MultipartFile coverFile) {
        if (coverFile == null || coverFile.isEmpty()) {
            throw new NoCoverException();
        }
        if (coverFile.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException();
        }
        if (!ALLOWED_CONTENT_TYPES.contains(coverFile.getContentType())) {
            throw new WrongFileException();
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        try {
            String storedFileName = fileStorageService.storeFile(
                    EVENTS_COVERS_PATH,
                    eventId.toString(),
                    coverFile
            );

            event.setCoverPath(storedFileName);
            Event savedEvent = eventRepository.save(event);

            return eventMapper.entityToResponse(savedEvent);

        } catch (FileStorageException  e) {
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

        try {
            fileStorageService.deleteFile(EVENTS_COVERS_PATH, event.getCoverPath());

            event.setCoverPath(null);
            eventRepository.save(event);

        } catch (FileStorageException e) {
            log.error("Ошибка при удалении файлов мероприятия {}: {}", eventId, e.getMessage());
            throw new ConflictException("Ошибка при удалении файла: " + e.getMessage());
        }
    }
}
