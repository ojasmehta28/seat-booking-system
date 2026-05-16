package com.seatbooking.controller;

import com.seatbooking.dto.BookingRequestDTO;
import com.seatbooking.dto.BookingResponseDTO;
import com.seatbooking.entity.Booking;
import com.seatbooking.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired; // importing necessary annotations for dependency injection
import org.springframework.web.bind.annotation.*; //importing necessary annotations for REST controller

@RestController
@RequestMapping("/bookings") // Base URL for all booking-related endpoints
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //Create booking API
    @PostMapping
    public BookingResponseDTO createBooking(

        @RequestBody BookingRequestDTO request) {

            Booking booking= bookingService.createBooking(
                request.getEventId(),
                request.getUserId(),
                request.getSeatIds()
            );
            
            return new BookingResponseDTO(

                booking.getId(),
                booking.getStatus(),
                booking.getTotalAmount()
            );

            
        }

        
    

}
