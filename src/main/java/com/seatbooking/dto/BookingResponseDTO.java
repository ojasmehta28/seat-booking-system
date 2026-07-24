package com.seatbooking.dto;

import com.seatbooking.enums.BookingStatus;

// import lombok.AllArgsConstructor;
// import lombok.Data;

// @Data
// @AllArgsConstructor

public class BookingResponseDTO { // DTO class to represent the response of a booking operation

    private Long bookingId; // ID of the booking

    private BookingStatus status; // Status of the booking (e.g., PAYMENT_PENDING, BOOKED, CANCELLED)

    private Double totalAmount; // Total amount for the booking

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
public BookingResponseDTO(Long bookingId,
                          BookingStatus status,
                          Double totalAmount) {

    this.bookingId = bookingId;
    this.status = status;
    this.totalAmount = totalAmount;
}
}
