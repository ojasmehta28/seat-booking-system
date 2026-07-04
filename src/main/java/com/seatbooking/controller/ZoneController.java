package com.seatbooking.controller;

import com.seatbooking.dto.ZoneRequestDTO;
import com.seatbooking.dto.ZoneResponseDTO;
import com.seatbooking.service.ZoneService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    /**
     * Create a new Zone
     */
    @PostMapping
    public ZoneResponseDTO createZone(

            @Valid

            @RequestBody

            ZoneRequestDTO request) {

        return zoneService.createZone(request);
    }

    /**
     * Fetch all Zones of an Event
     */
    @GetMapping("/{eventId}")
    public List<ZoneResponseDTO> getZonesByEvent(

            @PathVariable Long eventId) {

        return zoneService.getZonesByEvent(eventId);
    }
}