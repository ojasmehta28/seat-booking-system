package com.seatbooking.entity;

import jakarta.persistence.*;
// import lombok.*;

@Entity
@Table(name = "zone")
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zoneName; // VIP / FRONT / BACK

    private Double price; 

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event; // Many zones can belong to one event

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}