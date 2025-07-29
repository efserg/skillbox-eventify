package com.skillbox.eventify.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.skillbox.eventify.model.NotificationPreferences;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.schema.NotificationPreference;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface NotificationPreferenceMapper {
    NotificationPreferences entityToResponse(NotificationPreference entity);
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "notifyBeforeHours", source = "request.notifyBeforeHours")
    @Mapping(target = "notifyNewEvents", source = "request.notifyNewEvents")
    @Mapping(target = "notifyUpcoming", source = "request.notifyUpcoming")
    NotificationPreference requestToEntity(NotificationPreferences request, UserInfo user);

}
