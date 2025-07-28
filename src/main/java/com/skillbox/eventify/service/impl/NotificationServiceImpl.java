package com.skillbox.eventify.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skillbox.eventify.exception.InvalidVerificationCodeException;
import com.skillbox.eventify.exception.VerificationCodeExpiredException;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.TelegramLinkRepository;
import com.skillbox.eventify.repository.UserRepository;
import com.skillbox.eventify.schema.TelegramLink;
import com.skillbox.eventify.schema.TelegramLinkStatus;
import com.skillbox.eventify.schema.User;
import com.skillbox.eventify.service.CodeGenerator;
import com.skillbox.eventify.service.NotificationService;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private static final int LINK_CODE_LENGTH = 8;

    private final CodeGenerator codeGenerator;
    private final TelegramLinkRepository telegramLinkRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String linkTelegram(UserInfo user) {
        final String verificationCode = codeGenerator.generate(LINK_CODE_LENGTH);

        final Long userId = user.getId();

        TelegramLink telegramLink = telegramLinkRepository.findByUserId(userId)
                .orElse(new TelegramLink());

        telegramLink.setUser(User.builder().id(userId).build());
        telegramLink.setVerificationCode(verificationCode);
        telegramLink.setExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES));
        telegramLink.setStatus(TelegramLinkStatus.PENDING);

        telegramLinkRepository.save(telegramLink);

        return verificationCode;
    }

    @Override
    @Transactional
    public void confirmLink(Long chatId, String code) {
        TelegramLink link = telegramLinkRepository.findByVerificationCode(code)
                .orElseThrow(InvalidVerificationCodeException::new);

        if (link.getExpiresAt().isBefore(Instant.now())) {
            throw new VerificationCodeExpiredException();
        }

        User user = link.getUser();
        user.setTelegramChatId(chatId);
        userRepository.save(user);

        link.setStatus(TelegramLinkStatus.CONFIRMED);
        telegramLinkRepository.save(link);
    }
}
