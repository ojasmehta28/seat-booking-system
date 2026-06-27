package com.seatbooking.dto;

public class SeatRequestDTO {

    // Seat Number
    private String seatNumber;

    // Event ID
    private Long eventId;

    // Zone ID
    private Long zoneId;

    // Getter and Setter for Seat Number
    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Getter and Setter for Event ID
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    // Getter and Setter for Zone ID
    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}