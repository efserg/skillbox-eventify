package com.skillbox.eventify.mapper;

import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.schema.Booking;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        imports = {User.class, Event.class, Instant.class, ChronoUnit.class},
        uses = {EventMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BookingMapper {

    @Mapping(target = "customerEmail", source = "booking.user.email")
    @Mapping(target = "timezone", source = "booking.user.timezone")
    BookingResponse entityToResponse(Booking booking);

    default Page<BookingResponse> entityToResponse(Page<Booking> source) {
        return source.map(this::entityToResponse);
    }

    List<BookingResponse> entityToResponse(List<Booking> bookings);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "expiryTime", expression = "java(Instant.now().plus(3, ChronoUnit.DAYS))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "confirmed", constant = "false")
    @Mapping(target = "bookingTime", source = "event.dateTime")
    @Mapping(target = "user", expression = "java(User.builder().id(user.getId()).build())")
    @Mapping(target = "event", source = "event")
    Booking requestToEntity(CreateBookingRequest request, Event event, UserInfo user);
}
