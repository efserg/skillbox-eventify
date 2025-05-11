package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
        public static LevelEnum fromValue(String value) {
            for (LevelEnum b : LevelEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    public ErrorResponse addDetailsItem(ErrorDetail detailsItem) {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        this.details.add(detailsItem);
        return this;
    }
}

