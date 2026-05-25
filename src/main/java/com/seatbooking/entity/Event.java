package com.seatbooking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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

    
    @NotBlank(message = "Event name is required")
    private String name;

    
    @NotBlank(message = "Event type is required")
    private String eventType;

    
    @NotNull(message = "Event start time is required")
    private LocalDateTime eventStartTime;

    
    @NotNull(message = "Booking open time is required")
    private LocalDateTime bookingOpenTime;

    
    @NotNull(message = "Booking close time is required")
    private LocalDateTime bookingCloseTime;

    
    @Min(value = 1, message = "Minimum seat limit should be 1")
    private Integer maxSeatsPerUser;
}