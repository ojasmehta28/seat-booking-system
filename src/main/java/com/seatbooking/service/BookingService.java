package com.seatbooking.service;

import com.seatbooking.SeatbookingApplication;
import com.seatbooking.bookingengine.BookingEngine;
import com.seatbooking.entity.Booking;
import com.seatbooking.entity.BookingSeat;
import com.seatbooking.entity.Event;
import com.seatbooking.entity.Seat;

import com.seatbooking.repository.BookingRepository;
import com.seatbooking.repository.BookingSeatRepository;
import com.seatbooking.repository.SeatRepository;
import com.seatbooking.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;


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

    //Main booking workflow
    @Transactional
    public Booking createBooking(Long eventId,
                                Long userId,
                                List<Long> seatIds){
        //Step 1- Fetch event 
        Event event= eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        //Step 2- Validate booking window
        if(!bookingEngine.isBookingAllowed(event)){

            throw new RuntimeException("Booking is not allowed now");
        }

        //Step 3- Fetch Seats
        List<Seat> seats= seatRepository.findAllById(seatIds);

        //Step 4- Validate seat count
        if(seatIds.size() > event.getMaxSeatsPerUser()){

            throw new RuntimeException("Seat Limit Exceed");
        }      
        
        //Step 5- Validate seat availability
        if(!bookingEngine.areSeatAvailable(seats)){
            throw new RuntimeException("One or more seats are not available");
        }

        //Step 6- Lock seats
        Booking booking = new Booking();

        booking.setUserId(userId);

        booking.setEvent(event);

        booking.setStatus("CONFIRMED");

        booking.setCreatedAt(LocalDateTime.now());

        //Calculate total amount
        double totalAmount=0;

        for(Seat seat: seats){

            totalAmount += seat.getZone().getPrice();
        }

        booking.setTotalAmount(totalAmount);

        //Step 8- Save booking
        Booking savedBooking = bookingRepository.save(booking);

        //Step 9- Map booking to seats
        for (Seat seat: seats){

            BookingSeat bookingSeat= new BookingSeat();
            bookingSeat.setBooking(savedBooking);
            bookingSeat.setSeat(seat);
            bookingSeatRepository.save(bookingSeat);

            //Final booking confirmation
            seat.setStatus("BOOKED");
            seatRepository.save(seat);
        }
        return savedBooking;
    }

}
