package com.seatbooking.controller;

import com.seatbooking.dto.EventRequestDTO;
import com.seatbooking.dto.EventResponseDTO;
import com.seatbooking.service.EventService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController { //Handling HTTP requests related to events, such as creating new events and fetching existing events

    @Autowired
    private EventService eventService;

    /**
     * Client sends:
     * EventRequestDTO
     *
     * Returns:
     * EventResponseDTO
     */
    @PostMapping
    public EventResponseDTO createEvent(

            @Valid
            @RequestBody
            EventRequestDTO request) {

        return eventService.createEvent(request);
    }

    /**
     * Fetch All Events
     */
    @GetMapping
    public List<EventResponseDTO> getAllEvents() {

        return eventService.getAllEvents();
    }
}