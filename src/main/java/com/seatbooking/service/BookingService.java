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
import com.seatbooking.enums.BookingStatus;

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

    @Transactional
    public Booking createBooking(
            Long eventId,
            Long userId,
            List<Long> seatIds) {

        
        // Step 1 - Fetch Event
        
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new BookingException("Event not found"));

        
        // Step 2 - Fetch Seats
        
        List<Seat> seats = seatIds.stream()
                .map(seatRepository::findSeatForUpdate)
                .toList();

        
        // Step 3 - Booking Validations
        
        bookingEngine.validateBookingWindow(event);

        bookingEngine.validateBookingLimit(
                userId,
                event,
                seatIds.size());

        bookingEngine.validateSeatAvailability(seats);

        
        // Step 4 - Lock Seats
        
        bookingEngine.lockSeats(
                seats,
                userId);

        
        // Step 5 - Calculate Total Amount
        
        BigDecimal totalAmount =
                pricingEngine.calculateTotalAmount(seats);

        
        // Step 6 - Create Booking
        
        Booking booking = new Booking();

        booking.setUserId(userId);

        booking.setEvent(event);

        booking.setStatus(BookingStatus.PAYMENT_PENDING); //status field in Booking entity is of type BookingStatus enum

        booking.setCreatedAt(LocalDateTime.now());

        booking.setTotalAmount(
                totalAmount.doubleValue());

        
        // Step 7 - Save Booking
        
        Booking savedBooking =
                bookingRepository.save(booking);

        
        // Step 8 - Save Booking Seats
        
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