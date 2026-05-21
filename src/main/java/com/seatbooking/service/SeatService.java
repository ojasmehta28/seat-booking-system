package com.seatbooking.service;

import com.seatbooking.entity.Seat;
import com.seatbooking.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService { //Service for handling seat related operations

    @Autowired
    private SeatRepository seatRepository;

    
    // Create seat
    public Seat createSeat(Seat seat) {

        return seatRepository.save(seat);
    }

    
    // Fetch seats by event
    public List<Seat> getSeatsByEvent(Long eventId) {

        return seatRepository.findByEventId(eventId);
    }
}