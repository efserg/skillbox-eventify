package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание нового пользователя")
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Email
    @Schema(name = "email", description = "Электронная почта пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("email")
    private String email;

    @NotNull
    @Size(min = 8)
    @Schema(name = "password", description = "Пароль", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("password")
    private String password;

}

