package com.skillbox.eventify.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skillbox.eventify.exception.InvalidVerificationCodeException;
import com.skillbox.eventify.exception.NotificationPreferenceNotFoundException;
import com.skillbox.eventify.exception.VerificationCodeExpiredException;
import com.skillbox.eventify.mapper.NotificationPreferenceMapper;
import com.skillbox.eventify.model.NotificationPreferences;
import com.skillbox.eventify.model.UserInfo;
import com.skillbox.eventify.repository.EventRepository;
import com.skillbox.eventify.repository.NotificationRepository;
import com.skillbox.eventify.repository.TelegramLinkRepository;
import com.skillbox.eventify.repository.UserRepository;
import com.skillbox.eventify.schema.Event;
import com.skillbox.eventify.schema.NotificationPreference;
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
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final NotificationPreferenceMapper preferenceMapper;
    private final EventRepository eventRepository;
    private final TelegramBotNotificator botNotificator;

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

    @Override
    public NotificationPreferences getNotificationPreferences(UserInfo user) {
        NotificationPreference preference = notificationRepository.findByUserId(user.getId()).orElse(null);
        return preferenceMapper.entityToResponse(preference);
    }

    @Override
    @Transactional
    public void deleteNotificationPreferences(UserInfo user) {
        NotificationPreference preference = notificationRepository.findByUserId(user.getId()).orElseThrow(NotificationPreferenceNotFoundException::new);
        notificationRepository.delete(preference);
    }

    @Override
    public NotificationPreferences createOrUpdateNotification(NotificationPreferences preferences, UserInfo user) {
        NotificationPreference entity = notificationRepository.findByUserId(user.getId())
                .orElse(preferenceMapper.requestToEntity(preferences, user));

        if (preferences.getNotifyNewEvents() != null) {
            entity.setNotifyBeforeHours(preferences.getNotifyBeforeHours());
        }
        if (preferences.getNotifyNewEvents() != null) {
            entity.setNotifyNewEvents(preferences.getNotifyNewEvents());
        }
        if (preferences.getNotifyUpcoming() != null) {
            entity.setNotifyUpcoming(preferences.getNotifyUpcoming());
        }

        final NotificationPreference saved = notificationRepository.save(entity);

        return preferenceMapper.entityToResponse(saved);
    }


    @Scheduled(cron = "0 59 * * * *")
    public void checkAndSendNotifications() {
        Instant now = Instant.now();
        List<User> users = userRepository.findAllWithNotificationPreferences();

        for (User user : users) {
            NotificationPreference prefs = user.getNotificationPreference();
            Long chatId = user.getTelegramChatId();
            notifyNewEvents(prefs, now, chatId);
            notifyUserBookings(user, prefs, now, chatId);
        }
    }

    private void notifyUserBookings(User user, NotificationPreference prefs, Instant now, Long chatId) {
        if (prefs.getNotifyUpcoming()) {
            List<Event> upcomingEvents = eventRepository.findUpcomingEventsForUser(
                    user.getId(), now,
                    now.plus(prefs.getNotifyBeforeHours(), ChronoUnit.HOURS)
            );
            if (!upcomingEvents.isEmpty()) {
                String message = "Напоминание о мероприятиях:\n" +
                        upcomingEvents.stream()
                                .map(e -> "- " + e.getTitle() + " (через " + prefs.getNotifyBeforeHours() + " ч)")
                                .collect(Collectors.joining("\n"));
                botNotificator.sendNotification(chatId, message);
            }
        }
    }

    private void notifyNewEvents(NotificationPreference prefs, Instant now, Long chatId) {
        if (prefs.getNotifyNewEvents()) {
            List<Event> newEvents = eventRepository.findNewEventsSince(now.minus(1, ChronoUnit.HOURS));
            if (!newEvents.isEmpty()) {
                String message = "Новые мероприятия:\n" +
                        newEvents.stream()
                                .map(e -> "- " + e.getTitle() + " (" + e.getDateTime() + ")")
                                .collect(Collectors.joining("\n"));
                botNotificator.sendNotification(chatId, message);
            }
        }
    }
}
