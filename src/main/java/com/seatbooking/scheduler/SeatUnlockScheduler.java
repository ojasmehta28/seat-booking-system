package com.seatbooking.scheduler;

import com.seatbooking.entity.Booking;
import com.seatbooking.entity.BookingSeat;
import com.seatbooking.entity.Seat;
import com.seatbooking.enums.BookingStatus;
import com.seatbooking.enums.SeatStatus;
import com.seatbooking.repository.BookingRepository;
import com.seatbooking.repository.BookingSeatRepository;
import com.seatbooking.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeatUnlockScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(SeatUnlockScheduler.class);

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Runs every minute.
     *
     * Finds LOCKED seats whose lock time has expired.
     *
     * For each expired reservation:
     * 1. Marks the related PAYMENT_PENDING booking as EXPIRED.
     * 2. Releases the seat back to AVAILABLE.
     */
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void releaseExpiredLocks() {

        List<Seat> expiredSeats =
                seatRepository.findByStatusAndLockExpiryTimeBefore(
                        SeatStatus.LOCKED,
                        LocalDateTime.now()
                );

        for (Seat seat : expiredSeats) {

            List<BookingSeat> bookingSeats =
                    bookingSeatRepository.findBySeatId(seat.getId());

            for (BookingSeat bookingSeat : bookingSeats) {

                Booking booking = bookingSeat.getBooking();

                if (booking.getStatus() == BookingStatus.PAYMENT_PENDING) {

                    booking.setStatus(BookingStatus.EXPIRED);

                    bookingRepository.save(booking);

                    logger.info(
                            "Booking {} expired because payment was not completed.",
                            booking.getId()
                    );
                }
            }

            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setLockedByUser(null);
            seat.setLockExpiryTime(null);

            seatRepository.save(seat);
        }

        if (!expiredSeats.isEmpty()) {

            logger.info(
                    "{} expired seat locks released.",
                    expiredSeats.size()
            );
        }
    }
}