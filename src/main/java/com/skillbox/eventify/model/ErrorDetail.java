package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "field", example = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("field")
    private String field;

    @Schema(name = "message", example = "Должен быть валидным email адресом", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("message")
    private String message;
}

