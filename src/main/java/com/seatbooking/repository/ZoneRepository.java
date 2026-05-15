package com.seatbooking.repository;

import org.springframework.data.jpa.repository.jpaRepository; //importing JpaRepository to provide CRUD operations for Zone entity
import java.util.list;

public interface ZoneRepository extends JpaRepository<Zone, Long> { //extending JpaRepository to provide CRUD operations for Zone entity, with Long as the type of the primary key

    List<Zone> findByEventId(Long eventId); //custom query method to find zones by event ID

}
