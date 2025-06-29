package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.UserRepository;
import com.skillbox.eventify.schema.User;
import com.skillbox.eventify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo loadUserByUsername(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return UserInfo.builder().id(user.getId()).email(user.getEmail()).build();
    }
}