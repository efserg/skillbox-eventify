package com.skillbox.eventify.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.Booking;
import com.skillbox.eventify.schema.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>, JpaSpecificationExecutor<Event> {
}
