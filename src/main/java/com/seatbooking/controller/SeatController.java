package com.seatbooking.controller;

import com.seatbooking.dto.SeatRequestDTO;
import com.seatbooking.dto.SeatResponseDTO;
import com.seatbooking.entity.Seat;
import com.seatbooking.service.SeatService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
public class SeatController { //handle HTTP requests related to seat operations, such as creating a new seat and fetching seats by event ID

    @Autowired 
    private SeatService seatService;

    /**
     * Create a new Seat
     *
     * Client sends:
     * Seat Number
     * Event ID
     * Zone ID
     *
     * Service performs all business validations.
     */
    @PostMapping
    public SeatResponseDTO createSeat(

            @Valid
            @RequestBody
            SeatRequestDTO request) {

        return seatService.createSeat(request);
    }

    /**
     * Fetch all seats belonging to an Event
     */
    @GetMapping("/{eventId}")
    public List<Seat> getSeats(

            @PathVariable Long eventId) {

        return seatService.getSeatsByEvent(eventId);
    }
}