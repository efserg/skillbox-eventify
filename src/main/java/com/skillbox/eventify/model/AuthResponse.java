package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на запрос авторизации")
public class AuthResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "token", description = "Токен авторизации", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("token")
    private String token;

    @Schema(name = "role", description = "Роль пользователя", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("role")
    private RoleEnum role;

    @RequiredArgsConstructor
    public enum RoleEnum {
        USER("USER"),

        ADMIN("ADMIN");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static RoleEnum fromValue(String value) {
            for (RoleEnum b : RoleEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }
}

