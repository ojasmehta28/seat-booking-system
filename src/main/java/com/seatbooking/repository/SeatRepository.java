package com.seatbooking.repository;

import com.seatbooking.entity.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType; //specify the type of lock to be used in database operations
import org.springframework.data.jpa.repository.Lock; //specify a lock mode to be used in database operations
import org.springframework.data.jpa.repository.Query;//specify a custom query to be executed on the database
import org.springframework.data.repository.query.Param; //specify a parameter to be passed to the custom query

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

    @Lock(LockModeType.PESSIMISTIC_WRITE) //specify a pessimistic write lock to be used in database operations
    @Query("""
           SELECT s
           FROM Seat s
           WHERE s.id = :seatId 
           """)
    Seat findSeatForUpdate( 
            @Param("seatId") Long seatId 
        );
}