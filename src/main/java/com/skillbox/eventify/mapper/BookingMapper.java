package com.skillbox.eventify.mapper;

import com.skillbox.eventify.model.UpdateBookingRequest;
import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.User;
import java.time.Instant;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.schema.Booking;

@Mapper(componentModel = "spring",
        imports = {User.class, Event.class, Instant.class},
        unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface BookingMapper {


    @Mapping(target = "eventId", source = "booking.event.id")
    @Mapping(target = "customerEmail", source = "booking.user.email")
    @Mapping(target = "timezone", source = "booking.user.timezone")
    BookingResponse entityToResponse(Booking booking);

//    @Mapping(target = "expiryTime", expression = "java(Instant.now().plus(3, java.time.temporal.ChronoUnit.DAYS))")
    @Mapping(target = "expiryTime", ignore = true)
    @Mapping(target = "bookingTime", ignore = true)
    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "user", expression = "java(User.builder().id(user.getId()).build())")
//    @Mapping(target = "confirmed", constant = "false")
    @Mapping(target = "confirmed", ignore = true)
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "event", expression = "java(Event.builder().id(request.getEventId()).build())")
    @Mapping(target = "event", ignore = true)
    Booking requestToEntity(CreateBookingRequest request);

    default Page<BookingResponse> entityToResponse(Page<Booking> source) {
        return source.map(this::entityToResponse);
    }

    List<BookingResponse> entityToResponse(List<Booking> bookings);
}
