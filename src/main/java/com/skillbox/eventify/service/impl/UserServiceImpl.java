package com.skillbox.eventify.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.UserRepository;
import com.skillbox.eventify.schema.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserInfo loadUserByUsername(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new UserInfo(user);
    }
}