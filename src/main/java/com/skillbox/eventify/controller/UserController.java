package com.skillbox.eventify.controller;

import com.skillbox.eventify.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.NotificationPreferences;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Validated
@Tag(name = "User", description = "Настройки уведомлений пользователя")
public class UserController {

    @Operation(
            operationId = "userNotificationsDelete",
            summary = "Отменить уведомления",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "Настройки удалены"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            }
    )
    @DeleteMapping("/notifications")
    public ResponseEntity<Void> userNotificationsDelete() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "userNotificationsGet",
            summary = "Получить настройки уведомлений",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Настройки уведомлений", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationPreferences.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            }
    )
    @GetMapping("/notifications")
    public ResponseEntity<NotificationPreferences> userNotificationsGet() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "userNotificationsPut",
            summary = "Обновить настройки уведомлений",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Настройки обновлены"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Настройки уведомлений не найдены", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @PutMapping("/notifications")
    public ResponseEntity<Void> userNotificationsPut(
            @Parameter(name = "NotificationPreferences", description = "") @Valid @RequestBody(required = false) NotificationPreferences notificationPreferences) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Operation(
            operationId = "userTelegramLinkPost",
            summary = "Инициировать привязку Telegram",
            tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Код для /start команды в боте", content = {
                            @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Не авторизован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            }
    )
    @PostMapping(value = "/telegram/link", produces = {"text/plain"})
    public ResponseEntity<String> userTelegramLinkPost() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
