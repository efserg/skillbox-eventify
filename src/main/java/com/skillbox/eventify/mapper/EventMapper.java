package com.skillbox.eventify.mapper;

import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.User;
import com.skillbox.eventify.service.BookingService;
import com.skillbox.eventify.service.UserService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring", imports = {User.class}, unmappedSourcePolicy = ReportingPolicy.ERROR)
public abstract class EventMapper {

    @Autowired
    BookingService bookingService;

    @Mapping(target = "coverUrl", source = "coverPath")
    @Mapping(target = "availableTickets", qualifiedByName = "availableTickets", source = ".")
    public abstract EventResponse entityToResponse(Event event);

    public Page<EventResponse> toPageResponse(Page<Event> page) {
        return page.map(this::entityToResponse);
    }

    @Named("availableTickets")
    int availableTickets(Event event) {
        final Integer booked = bookingService.calculateBookedTickets(event.getId());
        return event.getTotalTickets() - booked;
    }

    @Mapping(target = "createdBy", expression = "java(User.builder().id(user.getId()).build())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coverPath", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    public abstract Event requestToEntity(EventCreateRequest request, @Context UserInfo user);
}
