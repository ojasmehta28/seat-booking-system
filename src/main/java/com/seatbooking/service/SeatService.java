package com.seatbooking.service;

import com.seatbooking.dto.SeatRequestDTO;
import com.seatbooking.dto.SeatResponseDTO;
import com.seatbooking.entity.Event;
import com.seatbooking.entity.Seat;
import com.seatbooking.entity.Zone;
import com.seatbooking.exception.BookingException;
import com.seatbooking.repository.EventRepository;
import com.seatbooking.repository.SeatRepository;
import com.seatbooking.repository.ZoneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeatService { //seat service used for business logic of seat related operations

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    /**
     * Create a new Seat
     *
     * Workflow:
     * 1. Validate Event
     * 2. Validate Zone
     * 3. Create Seat Entity
     * 4. Set Default Values
     * 5. Save Seat
     * 6. Return Response DTO
     */
    @Transactional
    public SeatResponseDTO createSeat(SeatRequestDTO request) {

        // Step 1 - Check whether Event exists
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new BookingException("Event not found"));

        // Step 2 - Check whether Zone exists
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() ->
                        new BookingException("Zone not found"));

        // Step 3 - Create Seat Entity
        Seat seat = new Seat();

        seat.setSeatNumber(request.getSeatNumber());

        seat.setEvent(event);

        seat.setZone(zone);

        // Step 4 - Initialize default values
        // Every newly created seat starts as AVAILABLE
        seat.setStatus("AVAILABLE");

        // No user has locked this seat yet
        seat.setLockedByUser(null);

        // No lock expiry time because seat is available
        seat.setLockExpiryTime(null);

        // Step 5 - Save Seat
        Seat savedSeat = seatRepository.save(seat);

        // Step 6 - Return Response DTO
        return new SeatResponseDTO(

                savedSeat.getId(),

                savedSeat.getSeatNumber(),

                savedSeat.getStatus()
        );
    }

    /**
     * Fetch all seats of a particular event
     */
    public List<Seat> getSeatsByEvent(Long eventId) {

        return seatRepository.findByEventId(eventId);
    }
}