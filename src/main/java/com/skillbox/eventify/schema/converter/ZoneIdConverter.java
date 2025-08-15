package com.skillbox.eventify.schema.converter;

import java.time.ZoneId;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZoneIdConverter implements AttributeConverter<ZoneId, String> {
    @Override
    public String convertToDatabaseColumn(ZoneId zoneId) {
        return zoneId != null ? zoneId.getId() : null;
    }

    @Override
    public ZoneId convertToEntityAttribute(String zoneIdString) {
        return zoneIdString != null ? ZoneId.of(zoneIdString) : null;
    }
}