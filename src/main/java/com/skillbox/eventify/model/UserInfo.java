package com.skillbox.eventify.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserInfo implements Serializable {
    UUID id;
    String username;
    String firstName;
    String lastName;
    String email;
}
