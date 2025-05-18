package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    private Integer id;

    @Schema(name = "eventId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("eventId")
    private Integer eventId;

    @Email
    @Schema(name = "customerEmail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("customerEmail")
    private String customerEmail;

    @Schema(name = "ticketCount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("ticketCount")
    private Integer ticketCount;

    @Valid
    @Schema(name = "bookingTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("bookingTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant bookingTime;

    @Valid
    @Schema(name = "expiryTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("expiryTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant expiryTime;

    @Schema(name = "confirmed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("confirmed")
    private Boolean confirmed;

    @Schema(name = "timezone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("timezone")
    private ZoneId timezone;

    @Schema(description = "Страница с бронированиями")
    public static class PageableBookingResponse extends PageImpl<BookingResponse> {
        public PageableBookingResponse(List<BookingResponse> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }
}

