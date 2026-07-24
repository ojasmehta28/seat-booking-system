package com.seatbooking.controller;

import com.seatbooking.dto.BookingRequestDTO;
import com.seatbooking.dto.BookingResponseDTO;
import com.seatbooking.entity.Booking;
import com.seatbooking.service.BookingService;

import jakarta.validation.Valid;

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

    @Valid    @RequestBody BookingRequestDTO request) { // Validating the incoming request body against the constraints defined in BookingRequestDTO

            Booking booking= bookingService.createBooking(
                request.getEventId(),
                request.getUserId(),
                request.getSeatIds()
            );
            
            return new BookingResponseDTO(

                booking.getId(),
                booking.getStatus().name(),
                booking.getTotalAmount()
            );

            
        }

        
    

}
