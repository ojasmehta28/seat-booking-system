package com.seatbooking.bookingengine;

import com.seatbooking.entity.Event;
import com.seatbooking.entity.Seat;
import com.seatbooking.exception.BookingException;
import com.seatbooking.repository.BookingRepository;
import com.seatbooking.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.seatbooking.enums.SeatStatus;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingEngine {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /*
     * 1. Booking should be opened.
     * 2. Booking should not be closed.
     * 3. Event should not have started.
     */
    public void validateBookingWindow(Event event) {

        LocalDateTime now = LocalDateTime.now();

        if (!(now.isAfter(event.getBookingOpenTime())
                && now.isBefore(event.getBookingCloseTime())
                && now.isBefore(event.getEventStartTime()))) {

            throw new BookingException(
                    "Booking is currently not allowed for this event.");
        }
    }

    /**
     * Validates maximum seats allowed per user.
     *
     * Example:
     *
     * Max Allowed = 5
     *
     * Already Booked = 3
     *
     * Requested = 3
     *
     * Total = 6
     *
     * Booking should fail.
     */
    public void validateBookingLimit(
            Long userId,
            Event event,
            int requestedSeats) {

        long alreadyBookedSeats =
                bookingRepository.countBookedSeatsByUserAndEvent(
                        userId,
                        event.getId());

        if (alreadyBookedSeats + requestedSeats
                > event.getMaxSeatsPerUser()) {

            long remainingSeats =
                    event.getMaxSeatsPerUser()
                            - alreadyBookedSeats;

            throw new BookingException(
                    "Booking limit exceeded. You can book only "
                            + remainingSeats
                            + " more seat(s) for this event.");
        }
    }

    /**
     * Validates seat availability.
     *
     * Every selected seat must be AVAILABLE.
     */
    public void validateSeatAvailability(List<Seat> seats) {

        for (Seat seat : seats) {

           if (seat.getStatus() != SeatStatus.AVAILABLE) { //status field is of type SeatStatus enum 

                throw new BookingException(
                        "One or more selected seats are not available.");
            }
        }
    }

    /**
     * Locks seats for 5 minutes.
     *
     * During this period
     * another user cannot book them.
     */
    public void lockSeats(
            List<Seat> seats,
            Long userId) {

        for (Seat seat : seats) {

            seat.setStatus(SeatStatus.LOCKED);

            seat.setLockedByUser(userId);

            seat.setLockExpiryTime(
                    LocalDateTime.now().plusMinutes(5));

            seatRepository.save(seat);
        }
    }
}