package com.skillbox.eventify.controller;

import com.skillbox.eventify.model.AuthResponse;
import com.skillbox.eventify.model.ErrorResponse;
import com.skillbox.eventify.model.LoginRequest;
import com.skillbox.eventify.model.RegisterRequest;
import com.skillbox.eventify.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Аутентификация и регистрация")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            operationId = "authLoginPost",
            summary = "Вход в систему",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный вход", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Неверное имя пользователя или пароль", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
            }
    )
    @PostMapping("/login")
    public AuthResponse authLoginPost(
            @Parameter(name = "LoginRequest", required = true) @Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }


    @Operation(
            operationId = "authRegisterPost",
            summary = "Регистрация пользователя",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Ошибка валидации логина или пароля", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @PostMapping("/register")
    public AuthResponse authRegisterPost(
            @Parameter(name = "RegisterRequest", required = true) @Valid @RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

}
