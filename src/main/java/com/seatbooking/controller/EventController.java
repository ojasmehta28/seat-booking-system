package com.seatbooking.controller;

import com.seatbooking.entity.Event;
import com.seatbooking.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController //Indicates that this class is a REST controller, handling HTTP requests and returning responses in JSON format
@RequestMapping("/events")
public class EventController { //Controller for handling event related requests

    @Autowired
    private EventService eventService;

    //Create new Event
    @PostMapping
    public Event createEvent(@RequestBody Event event){ //Endpoint to create a new event, accepting event details in the request body

        return eventService.createEvent(event);
    }

    //Fetch all events
    @GetMapping
    public List<Event> getAllEvents(){ //Endpoint to fetch all events

        return eventService.getAllEvents();
    }




}
