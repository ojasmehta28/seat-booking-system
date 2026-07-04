package com.seatbooking.dto;

public class BulkSeatResponseDTO { // DTO class to represent the response of bulk seat creation operation

    // Number of newly created seats
    private Integer createdSeats;

    // Number of duplicate seats skipped
    private Integer skippedSeats;

    // Final status message
    private String message;

    // Default Constructor
    public BulkSeatResponseDTO() {
    }

    // Parameterized Constructor
    public BulkSeatResponseDTO(
            Integer createdSeats,
            Integer skippedSeats,
            String message) {

        this.createdSeats = createdSeats;
        this.skippedSeats = skippedSeats;
        this.message = message;
    }

    public Integer getCreatedSeats() {
        return createdSeats;
    }

    public void setCreatedSeats(Integer createdSeats) {
        this.createdSeats = createdSeats;
    }

    public Integer getSkippedSeats() {
        return skippedSeats;
    }

    public void setSkippedSeats(Integer skippedSeats) {
        this.skippedSeats = skippedSeats;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}