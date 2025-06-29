package com.skillbox.eventify.controller;

import com.skillbox.eventify.service.EventService;
import java.time.OffsetDateTime;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.model.EventResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
@Validated
@Tag(name = "Events", description = "Управление мероприятиями")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @Operation(
            operationId = "eventsGet",
            summary = "Получить список мероприятий",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список мероприятий", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventResponse.PageableEventResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @GetMapping
    public Page<EventResponse> eventsGet(
            @Parameter(name = "from", description = "Фильтр от даты", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @Parameter(name = "to", description = "Фильтр до даты", in = ParameterIn.QUERY) @Valid @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to,
            @Parameter(description = "Параметры пагинации и сортировки") @PageableDefault(sort = "dateTime", direction = Direction.ASC, size = 20) Pageable pageable
    ) {
        return eventService.getEvents(from, to, pageable);
    }

    @Operation(
            operationId = "eventsIdGet",
            summary = "Получить мероприятие по ID",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Мероприятие", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public EventResponse eventsIdGet(
            @Parameter(name = "id", description = "Идентификатор мероприятия", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        return eventService.getEvent(id);
    }

}
