package com.seatbooking.controller;

import com.seatbooking.entity.Seat;
import com.seatbooking.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    
    // Create seat
    @PostMapping
    public Seat createSeat(@RequestBody Seat seat) {

        return seatService.createSeat(seat);
    }

    
    // Fetch seats by event
    @GetMapping("/{eventId}")
    public List<Seat> getSeats(@PathVariable Long eventId) {

        return seatService.getSeatsByEvent(eventId);
    }
}