package com.seatbooking.repository;

import com.seatbooking.entity.BookingSeat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingSeatRepository
        extends JpaRepository<BookingSeat, Long> {

    // Find all seat mappings for a booking
    List<BookingSeat> findByBookingId(Long bookingId);
}