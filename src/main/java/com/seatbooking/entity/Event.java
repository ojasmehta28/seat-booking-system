package com.seatbooking.entity;

import jakarta.persistence.*; // JPA annotations for entity mapping
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String eventType; // MOVIE / CRICKET / CONCERT

    private LocalDateTime eventStartTime; // When the event starts

    private LocalDateTime bookingOpenTime; // When the booking opens for the event

    private LocalDateTime bookingCloseTime; // When the booking closes for the event

    private Integer maxSeatsPerUser; // Maximum number of seats a user can book for this event
}