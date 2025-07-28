package com.skillbox.eventify.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.TelegramLink;

@Repository
public interface TelegramLinkRepository extends CrudRepository<TelegramLink, Long> {
    Optional<TelegramLink> findByUserId(Long id);

    Optional<TelegramLink> findByVerificationCode(String code);
}
