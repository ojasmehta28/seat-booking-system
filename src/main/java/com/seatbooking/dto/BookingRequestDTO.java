package com.seatbooking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BookingRequestDTO {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> seatIds;

    @NotNull(message = "User ID is required")
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