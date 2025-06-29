package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.exception.ConflictException;
import com.skillbox.eventify.exception.UnauthorizedException;
import com.skillbox.eventify.model.AuthResponse;
import com.skillbox.eventify.model.AuthResponse.RoleEnum;
import com.skillbox.eventify.model.LoginRequest;
import com.skillbox.eventify.model.RegisterRequest;
import com.skillbox.eventify.repository.UserRepository;
import com.skillbox.eventify.schema.User;
import com.skillbox.eventify.schema.User.Role;
import com.skillbox.eventify.service.AuthService;
import com.skillbox.eventify.service.JwtTokenProvider;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        String token = jwtTokenProvider.createToken(savedUser);

        return new AuthResponse(token, RoleEnum.USER);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException();
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UnauthorizedException::new);

        final String roleName = user.getRole().name();

        String token = jwtTokenProvider.createToken(user);

        return new AuthResponse(token, RoleEnum.fromValue(roleName));
    }
}
