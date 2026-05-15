package com.seatbooking.repository;

import com.seatbooking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository; //importing JpaRepository to provide CRUD operations for

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> { //extending JpaRepository to provide CRUD operations for BookingSeat entity, with Long as the type of the primary key

}
