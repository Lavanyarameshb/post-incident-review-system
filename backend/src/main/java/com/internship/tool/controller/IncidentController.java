package com.internship.tool.controller;

import com.internship.tool.dto.IncidentRequest;
import com.internship.tool.entity.Incident;
import com.internship.tool.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Incident>> getAllIncidents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Incident> incidents = incidentService.getAllIncidents(page, size);
        return ResponseEntity.ok(incidents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Incident incident = incidentService.getIncidentById(id);
        return ResponseEntity.ok(incident);
    }

    @PostMapping("/create")
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody IncidentRequest request) {
        Incident savedIncident = incidentService.createIncident(request);
        return new ResponseEntity<>(savedIncident, HttpStatus.CREATED);
    }
}