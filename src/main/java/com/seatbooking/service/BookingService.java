package com.seatbooking.service;

import com.seatbooking.bookingengine.BookingEngine;
import com.seatbooking.bookingengine.PricingEngine;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingEngine bookingEngine;

    @Autowired
    private PricingEngine pricingEngine;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    /**
     * Main Booking Workflow
     *
     * Workflow
     * ----------------------------------------------------
     * 1. Fetch Event
     * 2. Validate Booking Window
     * 3. Fetch Seats with Pessimistic Lock
     * 4. Validate Maximum Seats Per User
     * 5. Validate Seat Availability
     * 6. Lock Seats
     * 7. Calculate Total Amount
     * 8. Create Booking
     * 9. Save Booking
     * 10. Create Booking ↔ Seat Mapping
     *
     * Current Seat Flow
     *
     * AVAILABLE
     *      ↓
     * LOCKED
     *      ↓
     * PAYMENT_PENDING
     *
     * Future
     *
     * PAYMENT_PENDING
     *      ↓
     * PAYMENT_SUCCESS
     *      ↓
     * BOOKED
     *
     * OR
     *
     * PAYMENT_PENDING
     *      ↓
     * PAYMENT_FAILED
     *      ↓
     * AVAILABLE
     */
    @Transactional
    public Booking createBooking(
            Long eventId,
            Long userId,
            List<Long> seatIds) {

        // -------------------------------------------------
        // Step 1 - Fetch Event
        // -------------------------------------------------
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new BookingException("Event not found"));

        // -------------------------------------------------
        // Step 2 - Validate Booking Window
        // BookingEngine decides whether booking is allowed
        // based on Event timings.
        // -------------------------------------------------
        if (!bookingEngine.isBookingAllowed(event)) {

            throw new BookingException(
                    "Booking is currently not allowed for this event");
        }

        // -------------------------------------------------
        // Step 3 - Fetch Seats
        //
        // We fetch every requested seat using
        // PESSIMISTIC_WRITE locking.
        //
        // This prevents two users from booking
        // the same seat simultaneously.
        // -------------------------------------------------
        List<Seat> seats = seatIds.stream()
                .map(seatRepository::findSeatForUpdate)
                .toList();

        // -------------------------------------------------
        // Step 4 - Validate Maximum Seats Per User
        // -------------------------------------------------
        if (seatIds.size() > event.getMaxSeatsPerUser()) {

            throw new BookingException(
                    "Maximum "
                            + event.getMaxSeatsPerUser()
                            + " seats can be booked for this event");
        }

        // -------------------------------------------------
        // Step 5 - Validate Seat Availability
        // -------------------------------------------------
        if (!bookingEngine.areSeatAvailable(seats)) {

            throw new BookingException(
                    "One or more selected seats are not available");
        }

        // -------------------------------------------------
        // Step 6 - Lock Seats
        //
        // Seats are temporarily locked so that
        // another user cannot book them while
        // payment is in progress.
        // -------------------------------------------------
        bookingEngine.lockSeats(seats, userId);

        // -------------------------------------------------
        // Step 7 - Calculate Total Booking Amount
        //
        // BookingService should NEVER know
        // how prices are calculated.
        //
        // PricingEngine is responsible for
        // every pricing-related business rule.
        //
        // Current Rule
        // ------------
        // Sum of all selected seat prices.
        //
        // Future Rules
        // ------------
        // Coupon
        // Festival Discount
        // Membership Discount
        // Corporate Discount
        // GST
        // Convenience Fee
        // Dynamic Pricing
        // -------------------------------------------------
        BigDecimal totalAmount =
                pricingEngine.calculateTotalAmount(seats);

        // -------------------------------------------------
        // Step 8 - Create Booking
        // -------------------------------------------------
        Booking booking = new Booking();

        booking.setUserId(userId);

        booking.setEvent(event);

        // Payment is not completed yet.
        booking.setStatus("PAYMENT_PENDING");

        booking.setCreatedAt(LocalDateTime.now());

        // Booking entity currently stores Double.
        //
        // PricingEngine already works with
        // BigDecimal because financial values
        // require exact precision.
        //
        // This conversion is temporary.
        //
        // Later the Booking entity itself
        // will also use BigDecimal.
        booking.setTotalAmount(
                totalAmount.doubleValue());

        // -------------------------------------------------
        // Step 9 - Save Booking
        // -------------------------------------------------
        Booking savedBooking =
                bookingRepository.save(booking);

        // -------------------------------------------------
        // Step 10 - Create Booking ↔ Seat Mapping
        //
        // Each selected seat gets one mapping
        // with the booking.
        // This allows one booking to contain
        // multiple seats.
        // -------------------------------------------------
        for (Seat seat : seats) {

            BookingSeat bookingSeat =
                    new BookingSeat();

            bookingSeat.setBooking(savedBooking);

            bookingSeat.setSeat(seat);

            bookingSeatRepository.save(bookingSeat);
        }

        return savedBooking;
    }
}