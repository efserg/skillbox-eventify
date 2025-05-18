package com.skillbox.eventify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.schema.Booking;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface BookingMapper {


    @Mapping(target = "eventId", source = "booking.event.id")
    @Mapping(target = "customerEmail", source = "booking.user.email")
    @Mapping(target = "timezone", source = "booking.user.timezone")
    BookingResponse map(Booking booking);

    default Page<BookingResponse> map(Page<Booking> source) {
        return source.map(this::map);
    }
}
