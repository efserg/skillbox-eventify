package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillbox.eventify.exception.BusinessException.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Сообщение о произошедшей ошибке")
public class ErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(name = "code", example = "VALIDATION_FAILED", description = "Машинно-читаемый код ошибки", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("code")
    private String code;

    @NotNull
    @Schema(name = "level", example = "error", description = "Уровень серьезности", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("level")
    private LevelEnum level;

    @NotNull
    @Schema(name = "message", example = "Некоторые поля заполнены неверно", description = "Человеко-читаемое сообщение", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("message")
    private String message;

    @Valid
    @Schema(name = "details", description = "Детали ошибки (опционально)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("details")
    @Singular
    private List<@Valid ErrorDetail> details;

    /**
     * Уровень серьезности
     */
    @Getter
    @AllArgsConstructor
    @ToString
    public enum LevelEnum {
        INFO("info"),

        WARNING("warning"),

        ERROR("error");

        private final String value;

        @JsonCreator
        public static LevelEnum fromValue(@NotNull Level lvl) {
            return LevelEnum.valueOf(lvl.name());
        }
    }
}

