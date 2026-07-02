package com.seatbooking.dto;

public class ZoneRequestDTO {

    // Name of the Zone
    // Example:
    // VIP
    // GOLD
    // SILVER
    private String zoneName;

    // Ticket price for this Zone
    // Example:
    // 5000
    // 2500
    private Double price;

    // Event to which this Zone belongs
    // Example:
    // Event ID = 3
    private Long eventId;

    // Getter and Setter for Zone Name
    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    // Getter and Setter for Price
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // Getter and Setter for Event ID
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}