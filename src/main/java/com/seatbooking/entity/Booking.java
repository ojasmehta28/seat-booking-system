package com.seatbooking.entity;

import jakarta.persistence.*;
// import lombok.*;

import java.time.LocalDateTime;

import com.seatbooking.enums.BookingStatus;

@Entity
@Table(name = "booking")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Booking {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookingStatus getStatus() { //status field is of type BookingStatus enum
        return status;
    }

    public void setStatus(BookingStatus status) { //it is an enum type and we want to store the enum value in the database as a string
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    /*
     * Current booking status.
     *
     * EnumType.STRING stores enum names inside the database.
     * EnumType.ORDINAL stores enum ordinal values inside the database.
     * PAYMENT_PENDING
     * CONFIRMED
     * FAILED
     */
    @Enumerated(EnumType.STRING)
    private BookingStatus status; 

    private Double totalAmount;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}