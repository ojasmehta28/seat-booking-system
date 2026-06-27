package com.seatbooking.dto;

import lombok.Data;

@Data
public class SeatRequestDTO { 

    // Seat Number
    // Example:
    // A1
    // A2
    // B15
    private String seatNumber;

    // Event to which this seat belongs
    // Example:
    // Event ID = 3
    private Long eventId;

    // Zone of the seat
    // Example:
    // VIP
    // GOLD
    // SILVER
    private Long zoneId;
}