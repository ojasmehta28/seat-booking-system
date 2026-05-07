package com.seatbooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName; // VIP / FRONT / BACK

    private Double price; 

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // Many zones can belong to one event
}