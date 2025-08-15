package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание бронирования")
public class CreateBookingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(name = "eventId", description = "Идентификатор мероприятия", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("eventId")
    private Long eventId;

    @NotNull
    @Min(1)
    @Schema(name = "ticketCount", description = "Количество билетов", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("ticketCount")
    private Integer ticketCount;

}

