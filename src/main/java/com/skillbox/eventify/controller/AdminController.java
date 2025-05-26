package com.skillbox.eventify.controller;

import com.skillbox.eventify.model.BookingResponse;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "Административные функции")
@Validated
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookingService bookingService;

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
            @Parameter(description = "Параметры пагинации и сортировки") @PageableDefault(sort = "bookingTime", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return bookingService.findAll(pageable);
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
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        throw new NotImplementedException();
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
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        throw new NotImplementedException();
    }

}
