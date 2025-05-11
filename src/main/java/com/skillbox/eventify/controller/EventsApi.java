package com.skillbox.eventify.controller;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.EventUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
@Validated
@Tag(name = "Events", description = "Управление мероприятиями")
public class EventsApi {

    @Operation(
            operationId = "eventsGet",
            summary = "Получить список мероприятий",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список мероприятий", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = EventResponse.class)))
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
    public ResponseEntity<List<EventResponse>> eventsGet(
            @Parameter(name = "from", description = "Фильтр от даты", in = ParameterIn.QUERY) @Valid @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @Parameter(name = "to", description = "Фильтр до даты", in = ParameterIn.QUERY) @Valid @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "eventsIdCoverDelete",
            summary = "Удалить обложку мероприятия (Admin)",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Обложка удалена"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @DeleteMapping("/{id}/cover")
    public ResponseEntity<Void> eventsIdCoverDelete(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    @Operation(
            operationId = "eventsIdCoverPost",
            summary = "Загрузить обложку мероприятия (Admin)",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Обложка обновлена", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "413", description = "Файл слишком большой", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @PostMapping("/{id}/cover")
    public ResponseEntity<EventResponse> eventsIdCoverPost(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
            @Parameter(name = "cover", description = "Файл изображения (JPEG/PNG, макс. 5MB)") @RequestPart(value = "cover", required = false) MultipartFile cover
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "eventsIdDelete",
            summary = "Удалить мероприятие (Admin)",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Мероприятие удалено"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eventsIdDelete(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
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
    public ResponseEntity<EventResponse> eventsIdGet(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "eventsIdPut",
            summary = "Обновить мероприятие (Admin)",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ресурс не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> eventsIdPut(
            @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
            @Parameter(name = "EventUpdateRequest", description = "") @Valid @RequestBody(required = false) EventUpdateRequest eventUpdateRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "eventsPost",
            summary = "Создать мероприятие (Admin)",
            tags = {"Events"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Конфликт", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @PostMapping
    public ResponseEntity<EventResponse> eventsPost(
            @Parameter(name = "EventCreateRequest", description = "") @Valid @RequestBody(required = false) EventCreateRequest eventCreateRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
