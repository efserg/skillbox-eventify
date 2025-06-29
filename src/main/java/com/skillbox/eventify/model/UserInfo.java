package com.skillbox.eventify.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo implements Serializable {
    Long id;
    String email;
}
