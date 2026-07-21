package com.seatbooking.service;

import com.seatbooking.entity.Booking;
import com.seatbooking.entity.BookingSeat;
import com.seatbooking.entity.Seat;
import com.seatbooking.exception.BookingException;
import com.seatbooking.repository.BookingRepository;
import com.seatbooking.repository.BookingSeatRepository;
import com.seatbooking.repository.SeatRepository;
import com.seatbooking.enums.BookingStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    /**
     * Simulates successful payment
     *
     * PAYMENT_PENDING
     * ↓
     * CONFIRMED
     *
     * LOCKED
     * ↓
     * BOOKED
     */
    @Transactional
    public String confirmPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingException("Booking not found"));

        if (!booking.getStatus().equals("PAYMENT_PENDING")) {

            throw new BookingException(
                    "Booking is not waiting for payment"
            );
        }

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBookingId(bookingId);

        for (BookingSeat bookingSeat : bookingSeats) {

            Seat seat = bookingSeat.getSeat();

            seat.setStatus(BookingStatus.BOOKED);

            seat.setLockedByUser(null);

            seat.setLockExpiryTime(null);

            seatRepository.save(seat);
        }

        return "Payment successful. Booking confirmed.";
    }
}