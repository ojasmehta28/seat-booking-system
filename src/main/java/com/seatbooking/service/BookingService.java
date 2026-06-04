package com.seatbooking.service;

import com.seatbooking.SeatbookingApplication;
import com.seatbooking.bookingengine.BookingEngine;
import com.seatbooking.entity.Booking;
import com.seatbooking.entity.BookingSeat;
import com.seatbooking.entity.Event;
import com.seatbooking.entity.Seat;
import com.seatbooking.exception.BookingException;
import com.seatbooking.repository.BookingRepository;
import com.seatbooking.repository.BookingSeatRepository;
import com.seatbooking.repository.EventRepository;
import com.seatbooking.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final SeatbookingApplication seatbookingApplication;

    @Autowired
    private BookingEngine bookingEngine;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    BookingService(SeatbookingApplication seatbookingApplication) {
        this.seatbookingApplication = seatbookingApplication;
    }

    /**
     * Main Booking Workflow
     *
     * Steps:
     * 1. Fetch Event
     * 2. Validate Booking Window
     * 3. Fetch Seats
     * 4. Validate Seat Limit
     * 5. Validate Seat Availability
     * 6. Lock Seats
     * 7. Create Booking
     * 8. Save Booking
     * 9. Map Booking ↔ Seats
     * 10. Mark Seats as BOOKED
     */
    @Transactional
    public Booking createBooking(
            Long eventId,
            Long userId,
            List<Long> seatIds) {

        // Step 1 - Fetch Event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new BookingException("Event not found"));

        // Step 2 - Validate Booking Window
        // Checks:
        // Booking Open Time
        // Booking Close Time
        // Event Start Time
        if (!bookingEngine.isBookingAllowed(event)) {

            throw new BookingException(
                    "Booking is currently not allowed for this event"
            );
        }

        // Step 3 - Fetch Selected Seats
        List<Seat> seats = seatRepository.findAllById(seatIds);

        // Step 4 - Validate Maximum Seat Limit
        // Example:
        // Movie -> 10 seats
        // Cricket -> 8 seats
        // Concert -> 4 seats
        if (seatIds.size() > event.getMaxSeatsPerUser()) {

            throw new BookingException(
                    "Maximum "
                            + event.getMaxSeatsPerUser()
                            + " seats can be booked for this event"
            );
        }

        // Step 5 - Validate Seat Availability
        if (!bookingEngine.areSeatAvailable(seats)) {

            throw new BookingException(
                    "One or more selected seats are not available"
            );
        }

        // Step 6 - Lock Seats
        // Prevents another user from booking
        // the same seats simultaneously
        bookingEngine.lockSeats(seats, userId);

        // Step 7 - Create Booking
        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setEvent(event);

        
        booking.setStatus("CONFIRMED");

        booking.setCreatedAt(LocalDateTime.now());

        // Calculate Total Amount
        double totalAmount = 0;

        for (Seat seat : seats) {
            totalAmount += seat.getZone().getPrice();
        }

        booking.setTotalAmount(totalAmount);

        // Step 8 - Save Booking
        Booking savedBooking = bookingRepository.save(booking);

        // Step 9 - Create Booking ↔ Seat Mapping
        for (Seat seat : seats) {

            BookingSeat bookingSeat = new BookingSeat();

            bookingSeat.setBooking(savedBooking);
            bookingSeat.setSeat(seat);

            bookingSeatRepository.save(bookingSeat);

            // Step 10 - Mark Seat as BOOKED
            seat.setStatus("BOOKED");

            seatRepository.save(seat);
        }

        return savedBooking;
    }
}