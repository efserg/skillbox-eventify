package com.skillbox.eventify.controller;

import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.model.EventCreateRequest;
import com.skillbox.eventify.model.EventResponse;
import com.skillbox.eventify.model.EventUpdateRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.service.BookingService;
import com.skillbox.eventify.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Admin", description = "Административные функции")
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookingService bookingService;

    private final EventService eventService;

    @Operation(
            operationId = "adminBookingsGet",
            summary = "Получить все бронирования (Admin)",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница с бронированиями",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = BookingResponse.PageableBookingResponse.class))
                            }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @GetMapping("/bookings")
    public Page<BookingResponse> adminBookingsGet(
            @Parameter(description = "Идентификатор мероприятия")
            @RequestParam(required = false) Long eventId,
            @Parameter(description = "Только неподтвержденные")
            @RequestParam(required = false) Boolean unconfirmedOnly,
            @Parameter(description = "Параметры пагинации и сортировки")
            @PageableDefault(sort = "bookingTime", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return bookingService.findAll(eventId, unconfirmedOnly, pageable);
    }

    @Operation(
            operationId = "adminBookingsIdDelete",
            summary = "Удалить бронирование (Admin)",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Бронирование удалено"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Бронирование не найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @DeleteMapping("/bookings/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void adminBookingsIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id
    ) {
        bookingService.delete(id);
    }

    @Operation(
            operationId = "adminBookingsIdConfirm",
            summary = "Подтвердить бронирование (Admin)",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Бронирование подтверждено"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Бронирование не найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @PutMapping("/bookings/{id}/confirm")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void adminBookingsIdConfirm(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id
    ) {
        bookingService.confirm(id);
    }

    @Operation(
            operationId = "eventsIdCoverDelete",
            summary = "Удалить обложку мероприятия (Admin)",
            tags = {"Admin"},
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
    @DeleteMapping("/events/{id}/cover")
    public void eventsIdCoverDelete(
            @Parameter(name = "id", description = "Идентификатор мероприятия", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id
    ) {
        eventService.deleteEventCover(id);
    }

    @Operation(
            operationId = "eventsIdCoverPost",
            summary = "Загрузить обложку мероприятия (Admin)",
            tags = {"Admin"},
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
    @PostMapping("/events/{id}/cover")
    public EventResponse eventsIdCoverPost(
            @Parameter(name = "id", description = "Идентификатор мероприятия", required = true, in = ParameterIn.PATH)
            @PathVariable("id") Long id,
            @Parameter(name = "cover", description = "Файл изображения (JPEG/PNG, макс. 5MB)")
            @RequestPart(value = "cover", required = false) MultipartFile cover
    ) {
        return eventService.uploadEventCover(id, cover);
    }

    @Operation(
            operationId = "eventsIdDelete",
            summary = "Удалить мероприятие (Admin)",
            tags = {"Admin"},
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
    @DeleteMapping("/events/{id}")
    public void eventsIdDelete(
            @Parameter(name = "id", description = "Идентификатор мероприятия", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id
    ) {
        eventService.deleteEvent(id);
    }

    @Operation(
            operationId = "eventsIdPut",
            summary = "Обновить мероприятие (Admin)",
            tags = {"Admin"},
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
    @PutMapping("/events/{id}")
    public EventResponse eventsIdPut(
            @Parameter(name = "id", description = "Идентификатор мероприятия", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
            @Parameter(name = "EventUpdateRequest", description = "") @Valid @RequestBody(required = false) EventUpdateRequest eventUpdateRequest) {
        throw new NotImplementedException();
    }

    @Operation(
            operationId = "eventsPost",
            summary = "Создать мероприятие (Admin)",
            tags = {"Admin"},
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
    @PostMapping("/events")
    public EventResponse eventsPost(
            @Parameter(name = "EventCreateRequest", description = "") @Valid @RequestBody(required = true) EventCreateRequest eventCreateRequest,
            @RequestAttribute("userInfo") UserInfo user) {
        return eventService.createEvent(eventCreateRequest, user);
    }
}
