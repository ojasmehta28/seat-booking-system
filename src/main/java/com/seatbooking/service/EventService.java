package com.seatbooking.service;

import com.seatbooking.entity.Event;
import com.seatbooking.repository.EventRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    //Create event
    public Event createEvent(Event event){
        return eventRepository.save(event);


    }

    //Fetch all events
    public List<Event> getAllEvents(){

        return eventRepository.findAll();

    }

}
