package com.seatbooking.dto;

public class ZoneResponseDTO {

    // Unique Zone ID generated after saving
    private Long zoneId;

    // Zone Name
    // Example:
    // VIP
    // GOLD
    // SILVER
    private String zoneName;

    // Price of this Zone
    private Double price;

    // Event ID to which this Zone belongs
    private Long eventId;

    // Default Constructor
    public ZoneResponseDTO() {
    }

    // Parameterized Constructor
    public ZoneResponseDTO(
            Long zoneId,
            String zoneName,
            Double price,
            Long eventId) {

        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.price = price;
        this.eventId = eventId;
    }

    // Getter and Setter for Zone ID
    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

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