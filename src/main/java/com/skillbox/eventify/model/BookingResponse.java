package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
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
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Бронирование")
public class BookingResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", description = "Идентификатор бронирования", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("id")
    private Long id;

    @Schema(name = "event", description = "Мероприятие", requiredMode = RequiredMode.REQUIRED)
    @JsonProperty("event")
    private EventResponse event;

    @Email
    @Schema(name = "customerEmail", description = "Электронная почта пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("customerEmail")
    private String customerEmail;

    @Schema(name = "ticketCount", description = "Количество билетов", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("ticketCount")
    private Integer ticketCount;

    @Valid
    @Schema(name = "createdAt", description = "Дата создания бронирования", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("createdAt")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant createdAt;

    @Valid
    @Schema(name = "expiryTime", description = "Дата, до которой бронирование должно быть подтверждено", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("expiryTime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant expiryTime;

    @Schema(name = "confirmed", description = "Признак подтвержденного броонирования", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("confirmed")
    private Boolean confirmed;

    @Schema(name = "timezone", description = "Таймзона", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("timezone")
    private ZoneId timezone;

    @Schema(description = "Страница с бронированиями")
    public static class PageableBookingResponse extends PageImpl<BookingResponse> {
        public PageableBookingResponse(List<BookingResponse> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }
}

