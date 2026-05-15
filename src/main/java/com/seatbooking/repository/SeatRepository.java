package com.seatbooking.repository;

import com.seatbooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository; //importing JpaRepository to provide CRUD operations for

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> { //extending JpaRepository to provide CRUD operations for Seat entity, with Long as the type of the primary key

    List<Seat> findByEventId(Long eventId); //custom query method to find seats by event ID

    List<Seat> findByEventIdAndStatus(Long eventId, String status); //custom query method to find seats by event ID and status

}
