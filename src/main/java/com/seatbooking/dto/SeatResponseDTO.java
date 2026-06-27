package com.seatbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatResponseDTO { // Sending response to the client after saving the seat

    // Unique ID generated after saving the seat
    private Long seatId;

    // Seat Number
    // Example: A1, B5
    private String seatNumber;

    // Current status of the seat
    // AVAILABLE / LOCKED / BOOKED
    private String status;
}