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
import com.seatbooking.dto.BulkSeatRequestDTO;
import com.seatbooking.dto.BulkSeatResponseDTO;
import com.seatbooking.enums.SeatStatus;

import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;

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
        seat.setStatus(SeatStatus.AVAILABLE);

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

    /**
     * Generate multiple seats in one request
     *
     * Workflow
     * 1. Validate Event
     * 2. Validate Zone
     * 3. Fetch Existing Seats
     * 4. Generate New Seats
     * 5. Save All Seats
     * 6. Return Summary
     */
    @Transactional
    public BulkSeatResponseDTO generateSeatsInBulk( //generates multiple seats in one request and returns a summary of the operation
            BulkSeatRequestDTO request) {
    
    // Step 1 - Validate Event
    Event event = eventRepository.findById(
    
            request.getEventId()
    
    ).orElseThrow(() ->
    
            new BookingException(
                    "Event not found")
    );
    
    // Step 2 - Validate Zone
    Zone zone = zoneRepository.findById(
    
            request.getZoneId()
    
    ).orElseThrow(() ->
    
            new BookingException(
                    "Zone not found")
    );

        // Step 3 - Fetch all existing seats
    List<Seat> existingSeats = seatRepository
            .findByEventIdAndZoneId(
                    request.getEventId(),
                    request.getZoneId()
            );
    
    // Store existing seat numbers inside a HashSet for fast lookup
    Set<String> existingSeatNumbers =
            new HashSet<>();
    
    for (Seat seat : existingSeats) {
    
        existingSeatNumbers.add(
                seat.getSeatNumber()
        );
    }
        
    // List that will store only newly generated seats
    List<Seat> newSeats =
            new ArrayList<>();
        
        
        
    // Counters for summary response
    int createdSeats = 0;
    
    int skippedSeats = 0;
    
    // Generate seat numbers
    for (int seatNumber = request.getStartNumber();
    
         seatNumber <= request.getEndNumber();
    
         seatNumber++) {
    
        // Example:
        // A + 1 = A1
        // A + 2 = A2
        String generatedSeatNumber =
                request.getSeatPrefix() + seatNumber;
    
        // Skip duplicate seats
        if (existingSeatNumbers.contains(
                generatedSeatNumber)) {
    
            skippedSeats++;
    
            continue;
        }
    
        // Create new Seat entity
        Seat seat = new Seat();
    
        seat.setSeatNumber(generatedSeatNumber);
    
        seat.setStatus(SeatStatus.AVAILABLE);
    
        seat.setLockedByUser(null);
    
        seat.setLockExpiryTime(null);
    
        seat.setEvent(event);
    
        seat.setZone(zone);
    
        // Store inside list
        newSeats.add(seat);
    
        createdSeats++;
    }
    
    
    // Step 4 - Save all newly generated seats
    seatRepository.saveAll(newSeats); //save all the newly generated seats in one go, which is more efficient than saving them one by one.

    
    // Step 5 - Prepare response message
    String message; //Generate a message based on the number of created and skipped seats. If no seats were created, it indicates that all requested seats already exist. If no seats were skipped, it indicates that all seats were created successfully. Otherwise, it indicates that the bulk seat generation completed with partial success.
    
    if (createdSeats == 0) {
    
        message = "All requested seats already exist.";
    
    } else if (skippedSeats == 0) {
    
        message = "All seats created successfully.";
    
    } else {
    
        message = "Bulk seat generation completed with partial success.";
    }
    
    // Step 6 - Return response
    return new BulkSeatResponseDTO(
    
            createdSeats,
    
            skippedSeats,
    
            message
        );
    }
}