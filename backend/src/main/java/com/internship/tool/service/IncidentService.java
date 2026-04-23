package com.internship.tool.service;

import com.internship.tool.entity.Incident;
import com.internship.tool.exception.ResourceNotFoundException;
import com.internship.tool.repository.IncidentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Cacheable(value = "incidents")
    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    @Cacheable(value = "incident", key = "#id")
    public Incident getIncidentById(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident createIncident(Incident incident) {
        return incidentRepository.save(incident);
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public Incident updateIncident(Long id, Incident updatedIncident) {
        Incident existingIncident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));

        existingIncident.setTitle(updatedIncident.getTitle());
        existingIncident.setDescription(updatedIncident.getDescription());
        existingIncident.setStatus(updatedIncident.getStatus());
        existingIncident.setSeverity(updatedIncident.getSeverity());

        return incidentRepository.save(existingIncident);
    }

    @CacheEvict(value = {"incidents", "incident"}, allEntries = true)
    public void deleteIncident(Long id) {
        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found with id: " + id));

        incidentRepository.delete(incident);
    }
}