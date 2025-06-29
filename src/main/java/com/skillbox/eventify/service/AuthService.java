package com.skillbox.eventify.service;

import com.skillbox.eventify.model.AuthResponse;
import com.skillbox.eventify.model.LoginRequest;
import com.skillbox.eventify.model.RegisterRequest;

public interface AuthService {
    AuthResponse registerUser(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}
