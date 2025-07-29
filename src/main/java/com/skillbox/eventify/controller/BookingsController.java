package com.skillbox.eventify.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.model.CreateBookingRequest;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.model.UpdateBookingRequest;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.service.BookingService;
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
@RequestMapping("/bookings")
@Validated
@Tag(name = "Bookings", description = "Операции с бронированиями")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingService bookingService;

    @Operation(
            operationId = "bookingsGet",
            summary = "Получить свои бронирования",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookingResponse.class)))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            },
            security = {
                    @SecurityRequirement(name = "userAuth")
            }
    )
    @GetMapping
    public List<BookingResponse> getAll(@AuthenticationPrincipal UserInfo user) {
        return bookingService.findByUserId(user.getId());
    }

    @Operation(
            operationId = "bookingsIdDelete",
            summary = "Отменить бронирование",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Бронирование отменено"),
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
                    @SecurityRequirement(name = "userAuth")
            }
    )
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(name = "id", description = "Идентификатор бронирования", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @AuthenticationPrincipal UserInfo user
    ) {
        bookingService.cancelBooking(id, user);
    }


    @Operation(
            operationId = "bookingsIdGet",
            summary = "Получить бронирование по ID",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class)),
                    }),
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
                    @SecurityRequirement(name = "userAuth")
            }
    )
    @GetMapping("/{id}")
    public BookingResponse getById(
            @Parameter(name = "id", description = "Идентификатор бронирования", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @AuthenticationPrincipal UserInfo user
    ) {
        return bookingService.getById(id, user);
    }

    @Operation(
            operationId = "bookingsIdPut",
            summary = "Обновить бронирование",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Updated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
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
                    @SecurityRequirement(name = "userAuth")
            }
    )
    @PutMapping("/{id}")
    public BookingResponse edit(
            @Parameter(name = "id", description = "Идентификатор бронирования", required = true, in = ParameterIn.PATH) @PathVariable("id") Long id,
            @Parameter(name = "BookingUpdateRequest", description = "") @Valid @RequestBody(required = false) UpdateBookingRequest bookingUpdateRequest,
            @AuthenticationPrincipal UserInfo user) {
        return bookingService.update(id, bookingUpdateRequest, user);
    }

    @Operation(
            operationId = "bookingsPost",
            summary = "Создать бронирование",
            tags = {"Bookings"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Мероприятие не найдено", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(name = "userAuth")
            }
    )
    @PostMapping
    public BookingResponse create(
            @Parameter(name = "CreateBookingRequest") @Valid @RequestBody(required = false) CreateBookingRequest createBookingRequest,
            @AuthenticationPrincipal UserInfo user) {
        return bookingService.createBooking(createBookingRequest, user);
    }
}
