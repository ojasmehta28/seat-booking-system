package com.seatbooking.bookingengine;


import com.seatbooking.entity.Event;
import com.seatbooking.entity.Seat;
import com.seatbooking.repository.SeatRepository;
import com.seatbooking.bookingengine.PricingEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingEngine {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PricingEngine pricingEngine;

    //Validate booking window
    public boolean isBookingAllowed(Event event){

        LocalDateTime now= LocalDateTime.now();

        return now.isAfter(event.getBookingOpenTime()) &&
               now.isBefore(event.getBookingCloseTime()) &&
               now.isBefore(event.getEventStartTime());
    }

    //Validate seat availability
    public boolean areSeatAvailable(List<Seat> seats){

        for( Seat seat: seats){

            if(!seat.getStatus().equals("AVAILABLE")){
                return false;
            }
        }
        return true;
    }

    //Locks seats temporarily during booking process
    public void lockSeats(List<Seat> seats, Long userId){

        for (Seat seat: seats){
            seat.setStatus("LOCKED");
            seat.setLockedByUser(userId);

            //Lock expires after 5 minutes
            seat.setLockExpiryTime(LocalDateTime.now().plusMinutes(5));
            seatRepository.save(seat);
        }
    }

}
