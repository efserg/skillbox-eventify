package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    @Schema(name = "title", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("title")
    private String title;

    @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("description")
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Valid
    @Schema(name = "dateTime", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("dateTime")
    private OffsetDateTime dateTime;

    @Min(1)
    @Schema(name = "totalTickets", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalTickets")
    private Integer totalTickets;
}

