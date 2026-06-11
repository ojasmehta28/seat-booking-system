package com.seatbooking.scheduler;

import com.seatbooking.entity.Seat;
import com.seatbooking.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SeatUnlockScheduler {

    @Autowired
    private SeatRepository seatRepository;

    /**
     * Runs every minute
     *
     * Finds:
     * LOCKED seats
     * whose lock time has expired
     *
     * Converts them back to AVAILABLE
     */
    @Scheduled(fixedRate = 60000)
    public void releaseExpiredLocks() {

        List<Seat> expiredSeats =
                seatRepository.findByStatusAndLockExpiryTimeBefore(
                        "LOCKED",
                        LocalDateTime.now()
                );

        for (Seat seat : expiredSeats) {

            seat.setStatus("AVAILABLE");

            seat.setLockedByUser(null);

            seat.setLockExpiryTime(null);

            seatRepository.save(seat);
        }

        if (!expiredSeats.isEmpty()) {

            System.out.println(
                    expiredSeats.size()
                            + " expired seat locks released."
            );
        }
    }
}