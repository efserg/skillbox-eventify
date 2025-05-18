package com.skillbox.eventify.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Page<Booking> findAll(Pageable pageable);
}
