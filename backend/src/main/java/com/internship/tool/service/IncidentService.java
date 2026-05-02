package com.internship.tool.service;

import com.internship.tool.entity.Incident;
import com.internship.tool.exception.InvalidDataException;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(Incident incident) {
        validateIncident(incident);
        return incidentRepository.save(incident);
    }

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    private void validateIncident(Incident incident) {
        if (incident.getTitle() == null || incident.getTitle().trim().isEmpty()) {
            throw new InvalidDataException("Title is required");
        }

        if (incident.getSeverity() == null || incident.getSeverity().trim().isEmpty()) {
            throw new InvalidDataException("Severity is required");
        }

        if (incident.getStatus() == null || incident.getStatus().trim().isEmpty()) {
            throw new InvalidDataException("Status is required");
        }

        if (incident.getIncidentDate() == null) {
            throw new InvalidDataException("Incident date is required");
        }
    }
}