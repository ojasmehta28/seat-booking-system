package com.seatbooking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BookingRequestDTO { // DTO class to represent the incoming request for creating a booking

    @NotNull(message = "Event ID is required") // Validation annotation to ensure that eventId is not null
    private Long eventId;

    @NotEmpty(message = "At least one seat must be selected") // Validation annotation to ensure that the list of seatIds is not empty
    private List<Long> seatIds;

    @NotNull(message = "User ID is required") // Validation annotation to ensure that userId is not null
    private Long userId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}