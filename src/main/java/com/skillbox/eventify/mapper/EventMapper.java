package com.skillbox.eventify.mapper;

import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.BookingRepository;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        imports = {User.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class EventMapper {

    @Autowired
    BookingRepository bookingRepository;

    @Mapping(target = "coverUrl", expression = "java(event.getCoverPath() == null ? null : \"/images/\" + event.getCoverPath())")
    @Mapping(target = "availableTickets", qualifiedByName = "availableTickets", source = ".")
    public abstract EventResponse entityToResponse(Event event);

    public Page<EventResponse> toPageResponse(Page<Event> page) {
        return page.map(this::entityToResponse);
    }

    @Named("availableTickets")
    int availableTickets(Event event) {
        final Integer booked = bookingRepository.bookedCount(event.getId());
        return event.getTotalTickets() - booked;
    }

    @Mapping(target = "createdBy", expression = "java(User.builder().id(user.getId()).build())")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coverPath", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    public abstract Event requestToEntity(EventCreateRequest request, @Context UserInfo user);
}
