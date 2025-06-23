package com.skillbox.eventify.service;

import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request, UserDetails user);

    void deleteEvent(Long eventId);

    EventResponse uploadEventCover(Long eventId, MultipartFile coverFile);

    void deleteEventCover(Long eventId);
}
