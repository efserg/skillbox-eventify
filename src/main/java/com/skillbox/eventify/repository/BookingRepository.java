package com.skillbox.eventify.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    Page<Booking> findAll(Specification<Booking> spec, Pageable pageable);

    @Query("select sum(b.ticketCount) from Booking b where b.event.id = :eventId ")
    Integer bookedCount(Long eventId);

    @Modifying
    @Query("DELETE FROM Booking b WHERE b.event.id = :eventId")
    void deleteByEventId(Long eventId);
}
