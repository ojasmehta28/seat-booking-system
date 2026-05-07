package com.seatbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "seat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "seatNumber"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

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