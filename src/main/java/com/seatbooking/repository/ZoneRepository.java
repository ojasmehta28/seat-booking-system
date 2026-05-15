package com.seatbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository; //importing JpaRepository to provide CRUD operations for Zone entity

import com.seatbooking.entity.Zone;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> { //extending JpaRepository to provide CRUD operations for Zone entity, with Long as the type of the primary key

    List<Zone> findByEventId(Long eventId); //custom query method to find zones by event ID

}
