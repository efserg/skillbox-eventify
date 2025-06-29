package com.skillbox.eventify.service;

import com.skillbox.eventify.model.UserInfo;

public interface UserService {

    UserInfo loadUserByUsername(String username);
}
