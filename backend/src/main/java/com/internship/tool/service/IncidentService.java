package com.internship.tool.service;

import com.internship.tool.dto.IncidentRequest;
import com.internship.tool.entity.Incident;
import com.internship.tool.exception.InvalidDataException;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.IncidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(IncidentRequest request) {
        validateIncidentRequest(request);

        Incident incident = new Incident();
        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setSeverity(request.getSeverity());
        incident.setStatus(request.getStatus());
        incident.setReportedBy(request.getReportedBy());
        incident.setIncidentDate(request.getIncidentDate());

        return incidentRepository.save(incident);
    }

    public Page<Incident> getAllIncidents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return incidentRepository.findAll(pageable);
    }

    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    private void validateIncidentRequest(IncidentRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new InvalidDataException("Title is required");
        }

        if (request.getSeverity() == null || request.getSeverity().trim().isEmpty()) {
            throw new InvalidDataException("Severity is required");
        }

        if (request.getStatus() == null || request.getStatus().trim().isEmpty()) {
            throw new InvalidDataException("Status is required");
        }

        if (request.getIncidentDate() == null) {
            throw new InvalidDataException("Incident date is required");
        }
    }
}