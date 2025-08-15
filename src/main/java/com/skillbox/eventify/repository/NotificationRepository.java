package com.skillbox.eventify.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.NotificationPreference;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationPreference, Long> {
    Optional<NotificationPreference> findByUserId(Long id);
}
