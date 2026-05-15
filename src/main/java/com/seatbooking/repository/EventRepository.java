package com.seatbooking.repository;

import com.seatbooking.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository; //importing JpaRepository to provide CRUD operations for Event entity
//import org.springframework.stereotype.Repository; //importing Repository annotation to indicate that this interface is a repository component in Spring

//@Repository //indicates that this interface is a repository component in Spring, which will be responsible for data access and manipulation for the Event entity
public interface EventRepository extends JpaRepository<Event, Long> { //extending JpaRepository to provide CRUD operations for Event entity, with Long as the type of the primary key

}
