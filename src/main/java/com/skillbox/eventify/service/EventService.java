package com.skillbox.eventify.service;

import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.UserInfo;
import java.time.OffsetDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request, UserInfo user);

    void deleteEvent(Long eventId);

    EventResponse uploadEventCover(Long eventId, MultipartFile coverFile);

    void deleteEventCover(Long eventId);

    Page<EventResponse> getEvents(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    EventResponse getEvent(Long id);
}
