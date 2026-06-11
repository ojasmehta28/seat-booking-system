package com.seatbooking.repository;

import com.seatbooking.entity.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats belonging to an event
    List<Seat> findByEventId(Long eventId);

    // Find seats by event and status
    List<Seat> findByEventIdAndStatus(Long eventId, String status);

    // Find expired locked seats
    // Example:
    // Status = LOCKED
    // Lock Expiry Time < Current Time
    List<Seat> findByStatusAndLockExpiryTimeBefore(
            String status,
            LocalDateTime currentTime
    );
}