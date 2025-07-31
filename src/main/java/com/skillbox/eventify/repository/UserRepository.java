package com.skillbox.eventify.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
                SELECT u FROM User u
                LEFT JOIN FETCH u.notificationPreference np
                WHERE
                    np IS NOT NULL
                    AND (np.notifyNewEvents = true OR np.notifyUpcoming = true)
            """)
    List<User> findAllWithNotificationPreferences();
}
