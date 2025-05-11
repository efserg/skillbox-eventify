package com.skillbox.eventify.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.BookingResponse;
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
public class AdminController {


    @Operation(
            operationId = "adminBookingsGet",
            summary = "Получить все бронирования (Admin)",
            tags = {"Admin"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BookingResponse.class)))
                    })
            },
            security = {
                    @SecurityRequirement(name = "bearerAuth")
            }
    )
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingResponse>> adminBookingsGet() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
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
