package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    private Integer id;

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
    private Instant dateTime;

    @Schema(name = "totalTickets", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalTickets")
    private Integer totalTickets;

    @Schema(name = "availableTickets", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("availableTickets")
    private Integer availableTickets;

    @Schema(name = "coverUrl", example = "/uploads/events/1/cover.jpg", description = "URL обложки мероприятия", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("coverUrl")
    private String coverUrl;

    @Schema(description = "Страница с мероприятиями")
    public static class PageableEventResponse extends PageImpl<EventResponse> {

        public PageableEventResponse(List<EventResponse> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }
    }

}

