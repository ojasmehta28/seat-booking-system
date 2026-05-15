package com.seatbooking.dto;

import lombok.Data;
import java.util.List;
@Data
public class BookingRequestDTO {

    private Long eventId; // ID of the event for which the booking is being made

    private List<Long> seatIds; // List of seat IDs that the user wants to book

    private Long userId; // ID of the user making the booking

}
