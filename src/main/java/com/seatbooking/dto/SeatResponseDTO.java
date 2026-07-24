package com.seatbooking.dto;

import com.seatbooking.enums.SeatStatus;

public class SeatResponseDTO {

    private Long seatId;

    private String seatNumber;

    private SeatStatus status;

    public SeatResponseDTO() {
    }

    public SeatResponseDTO(
            Long seatId,
            String seatNumber,
            SeatStatus status) {

        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}