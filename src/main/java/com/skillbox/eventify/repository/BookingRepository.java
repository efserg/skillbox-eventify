package com.skillbox.eventify.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.skillbox.eventify.schema.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {
    @EntityGraph(attributePaths = {"event", "user"})
    Page<Booking> findAll(Specification<Booking> spec, Pageable pageable);
    @EntityGraph(attributePaths = {"event", "user"})
    List<Booking> findAllByUser_Id(Long userId);

    @Query("select COALESCE(sum(b.ticketCount), 0) from Booking b where b.event.id = :eventId ")
    Integer bookedCount(Long eventId);

    @Override
    @EntityGraph(attributePaths = {"event", "user"})
    Optional<Booking> findById(Long aLong);

    @Modifying
    @Query("DELETE FROM Booking b WHERE b.event.id = :eventId")
    void deleteByEventId(Long eventId);
}
