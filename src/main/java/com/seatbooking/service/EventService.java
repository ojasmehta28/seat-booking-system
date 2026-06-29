package com.seatbooking.service;

import com.seatbooking.dto.EventRequestDTO;
import com.seatbooking.dto.EventResponseDTO;
import com.seatbooking.entity.Event;
import com.seatbooking.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService { //Business logic of event related operations

    @Autowired
    private EventRepository eventRepository;

    /**
     * Workflow:
     * 1. Receive Request DTO
     * 2. Convert DTO → Entity
     * 3. Save Entity
     * 4. Convert Entity → Response DTO
     * 5. Return Response
     */
    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO request) {

        // Step 1 - Create Entity Object
        Event event = new Event();

        // Step 2 - Copy RequestDTO values into Entity
        event.setName(request.getName());

        event.setEventType(request.getEventType());

        event.setEventStartTime(request.getEventStartTime());

        event.setBookingOpenTime(request.getBookingOpenTime());

        event.setBookingCloseTime(request.getBookingCloseTime());

        event.setMaxSeatsPerUser(request.getMaxSeatsPerUser());

        // Step 3 - Save Entity
        Event savedEvent = eventRepository.save(event);

        // Step 4 - Convert Entity into ResponseDTO
        return new EventResponseDTO(

                savedEvent.getId(),

                savedEvent.getName(),

                savedEvent.getEventType(),

                savedEvent.getEventStartTime(),

                savedEvent.getBookingOpenTime(),

                savedEvent.getBookingCloseTime(),

                savedEvent.getMaxSeatsPerUser()
        );
    }

    /**
     * Fetch all Events
     */
    public List<EventResponseDTO> getAllEvents() {

        return eventRepository.findAll()

                .stream() //Convert the list of Event entities into a stream of Event entities, allowing for functional-style operations like mapping and filtering

                .map(event ->

                        new EventResponseDTO(

                                event.getId(),

                                event.getName(),

                                event.getEventType(),

                                event.getEventStartTime(),

                                event.getBookingOpenTime(),

                                event.getBookingCloseTime(),

                                event.getMaxSeatsPerUser()

                        )
                )

                .collect(Collectors.toList());
    }
}