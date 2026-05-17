package com.seatbooking.controller;

import com.seatbooking.entity.Event;
import com.seatbooking.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    //Create new Event
    @PostMapping
    public Event createEvent(@RequestBody Event event){

        return eventService.createEvent(event);
    }

    //Fetch all events
    @GetMapping
    public List<Event> getAllEvents(){

        return eventService.getAllEvents();
    }

    


}
