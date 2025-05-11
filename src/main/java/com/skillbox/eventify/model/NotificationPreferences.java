package com.skillbox.eventify.model;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferences implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "notifyNewEvents", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("notifyNewEvents")
    private Boolean notifyNewEvents = false;

    @Schema(name = "notifyUpcoming", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("notifyUpcoming")
    private Boolean notifyUpcoming = true;

    @Min(1)
    @Max(24)
    @Schema(name = "notifyBeforeHours", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("notifyBeforeHours")
    private Integer notifyBeforeHours = 24;

}

