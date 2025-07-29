package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на изменение бронирования")
public class UpdateBookingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Min(1)
    @Schema(name = "ticketCount", description = "Количество билетов", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("ticketCount")
    private Integer ticketCount;

}

