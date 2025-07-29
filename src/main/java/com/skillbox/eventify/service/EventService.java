package com.skillbox.eventify.service;

import java.time.OffsetDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.EventUpdateRequest;
import com.skillbox.eventify.model.UserInfo;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request, UserInfo user);

    void deleteEvent(Long eventId);

    Page<EventResponse> getEvents(OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    EventResponse getEvent(Long id);

    EventResponse update(Long id, EventUpdateRequest request);
}
