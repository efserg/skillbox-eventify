package com.skillbox.eventify.service;

import com.skillbox.eventify.schema.User;
import java.util.List;

public interface JwtTokenProvider {

    String createToken(User user);

    boolean validateToken(String token);

    String getUsername(String token);
}
