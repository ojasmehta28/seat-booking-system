package com.seatbooking.dto;

import java.time.LocalDateTime;

public class EventRequestDTO { //Transfer event data between client and server, specifically for creating or updating events

    // Name of the Event
    // Example:
    // IPL Final
    // Coldplay Concert
    private String name;

    // Type of Event
    // Example:
    // CRICKET
    // MOVIE
    // CONCERT
    private String eventType;

    // Actual start time of the event
    private LocalDateTime eventStartTime;

    // Booking opens from this time
    private LocalDateTime bookingOpenTime;

    // Booking closes at this time
    private LocalDateTime bookingCloseTime;

    // Maximum seats one user can book
    private Integer maxSeatsPerUser;

    // Getter and Setter for Event Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for Event Type
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // Getter and Setter for Event Start Time
    public LocalDateTime getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(LocalDateTime eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    // Getter and Setter for Booking Open Time
    public LocalDateTime getBookingOpenTime() {
        return bookingOpenTime;
    }

    public void setBookingOpenTime(LocalDateTime bookingOpenTime) {
        this.bookingOpenTime = bookingOpenTime;
    }

    // Getter and Setter for Booking Close Time
    public LocalDateTime getBookingCloseTime() {
        return bookingCloseTime;
    }

    public void setBookingCloseTime(LocalDateTime bookingCloseTime) {
        this.bookingCloseTime = bookingCloseTime;
    }

    // Getter and Setter for Maximum Seats Per User
    public Integer getMaxSeatsPerUser() {
        return maxSeatsPerUser;
    }

    public void setMaxSeatsPerUser(Integer maxSeatsPerUser) {
        this.maxSeatsPerUser = maxSeatsPerUser;
    }
}