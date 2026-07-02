package com.seatbooking.service;

import com.seatbooking.dto.ZoneRequestDTO;
import com.seatbooking.dto.ZoneResponseDTO;
import com.seatbooking.entity.Event;
import com.seatbooking.entity.Zone;
import com.seatbooking.exception.BookingException;
import com.seatbooking.repository.EventRepository;
import com.seatbooking.repository.ZoneRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private EventRepository eventRepository;

    /**
     * Create a new Zone
     *
     * Workflow
     * 1. Validate Event
     * 2. Create Zone Entity
     * 3. Save Zone
     * 4. Convert Entity → ResponseDTO
     */
    @Transactional
    public ZoneResponseDTO createZone(
            ZoneRequestDTO request) {

        Event event =
                eventRepository.findById(
                        request.getEventId())
                .orElseThrow(() ->
                        new BookingException(
                                "Event not found"));

        Zone zone = new Zone();

        zone.setZoneName(
                request.getZoneName());

        zone.setPrice(
                request.getPrice());

        zone.setEvent(event);

        Zone savedZone =
                zoneRepository.save(zone);

        return new ZoneResponseDTO(

                savedZone.getId(),

                savedZone.getZoneName(),

                savedZone.getPrice(),

                savedZone.getEvent().getId()
        );
    }

    /**
     * Fetch all Zones of an Event
     */
    public List<ZoneResponseDTO> getZonesByEvent(
            Long eventId) {

        return zoneRepository.findByEventId(eventId)

                .stream()

                .map(zone ->

                        new ZoneResponseDTO(

                                zone.getId(),

                                zone.getZoneName(),

                                zone.getPrice(),

                                zone.getEvent().getId()
                        ))

                .collect(Collectors.toList());
    }
}