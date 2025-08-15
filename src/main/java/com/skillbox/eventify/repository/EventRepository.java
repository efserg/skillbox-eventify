package com.skillbox.eventify.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    @Query("SELECT e FROM Event e WHERE e.dateTime >= :since")
    List<Event> findNewEventsSince(Instant since);

    @Query("""
        SELECT DISTINCT e FROM Event e 
        JOIN e.bookings b 
        WHERE b.user.id = :userId 
        AND e.dateTime > :currentTime 
        AND e.dateTime <= :notificationTime
    """)
    List<Event> findUpcomingEventsForUser(Long userId, Instant currentTime, Instant notificationTime
    );
}
