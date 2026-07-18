package com.seatbooking.repository;

import com.seatbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> { 

    @Query("""
            SELECT COUNT(bs)
            FROM BookingSeat bs
            WHERE bs.booking.userId = :userId 
              AND bs.booking.event.id = :eventId
              AND bs.booking.status = 'PAYMENT_PENDING'
            """)
    long countBookedSeatsByUserAndEvent(
            @Param("userId") Long userId,
            @Param("eventId") Long eventId
    );

}