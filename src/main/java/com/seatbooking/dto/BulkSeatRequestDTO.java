package com.seatbooking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BulkSeatRequestDTO {

    // Event for which seats will be generated
    @NotNull(message = "Event ID is required")
    private Long eventId;

    // Zone in which seats will be generated
    @NotNull(message = "Zone ID is required")
    private Long zoneId;

    // Prefix of the seat
    // Example:
    // A
    // VIP
    // G
    @NotBlank(message = "Seat prefix is required")
    private String seatPrefix;

    // Starting seat number
    // Example:
    // 1
    @Min(value = 1,
            message = "Start number must be greater than zero")
    private Integer startNumber;

    // Ending seat number
    // Example:
    // 100
    @Min(value = 1,
            message = "End number must be greater than zero")
    private Integer endNumber;

    public BulkSeatRequestDTO() {
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getSeatPrefix() {
        return seatPrefix;
    }

    public void setSeatPrefix(String seatPrefix) {
        this.seatPrefix = seatPrefix;
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
    }
}