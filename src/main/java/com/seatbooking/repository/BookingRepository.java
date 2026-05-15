package com.seatbooking.repository;

import com.seatbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository; //importing JpaRepository to provide CRUD operations for

public interface BookingRepository extends JpaRepository<Booking, Long> { //extending JpaRepository to provide CRUD operations for Booking entity, with Long as the type of the primary key

}
