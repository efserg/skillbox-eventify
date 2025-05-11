package com.skillbox.eventify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skillbox.eventify.model.AuthResponse;
import com.skillbox.eventify.model.LoginRequest;
import com.skillbox.eventify.model.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Аутентификация и регистрация")
public class AuthApi {

    @Operation(
            operationId = "authLoginPost",
            summary = "Вход в систему",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный вход", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    })
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authLoginPost(
            @Parameter(name = "LoginRequest", required = true) @Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }


    @Operation(
            operationId = "authRegisterPost",
            summary = "Регистрация пользователя",
            tags = {"Auth"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))
                    })
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> authRegisterPost(
            @Parameter(name = "RegisterRequest", required = true) @Valid @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
