package com.skillbox.eventify.service.impl;

import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    @Override
    @Transactional
    public String linkTelegram(UserInfo user) {
        return "12345678";
    }
}
