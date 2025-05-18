package com.skillbox.eventify.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.BookingResponse;
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
                    @ApiResponse(responseCode = "401", description = "Не авторизован"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещен")
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @GetMapping("/bookings")
    public Page<BookingResponse> adminBookingsGet(@Parameter(description = "Параметры пагинации и сортировки") @PageableDefault(sort = "bookingTime", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return bookingService.findAll(pageable);
    }

    @Operation(
            operationId = "adminBookingsIdDelete",
            summary = "Удалить бронирование (Admin)",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Бронирование удалено")
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @DeleteMapping("/bookings/{id}"
    )
    public ResponseEntity<Void> adminBookingsIdDelete(
            @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
