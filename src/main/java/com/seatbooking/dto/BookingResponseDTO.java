package com.seatbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BookingResponseDTO { // DTO class to represent the response of a booking operation

    private Long bookingId; // ID of the booking

    private String status; // Status of the booking (e.g., "CONFIRMED", "FAILED")

    private Double totalAmount; // Total amount for the booking

}
