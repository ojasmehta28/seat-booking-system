package com.seatbooking.entity;

import jakarta.persistence.*;
// import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seatNumber"}))
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Seat {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLockedByUser() {
        return lockedByUser;
    }

    public void setLockedByUser(Long lockedByUser) {
        this.lockedByUser = lockedByUser;
    }

    public LocalDateTime getLockExpiryTime() {
        return lockExpiryTime;
    }

    public void setLockExpiryTime(LocalDateTime lockExpiryTime) {
        this.lockExpiryTime = lockExpiryTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber; // Unique seat number within an event
 
    private String status; // AVAILABLE / LOCKED / BOOKED

    private Long lockedByUser; // User ID who has locked the seat

    private LocalDateTime lockExpiryTime; // Time until which the seat is locked, after which it becomes available again

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // Many seats can belong to one event

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone; // Many seats can belong to one zone
}