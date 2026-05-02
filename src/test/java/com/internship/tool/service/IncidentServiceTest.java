package com.internship.tool.service;

import com.internship.tool.entity.Incident;
import com.internship.tool.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncidentServiceTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private IncidentService incidentService;

    @Test
    void testCreateIncident() {
        Incident incident = new Incident();
        incident.setTitle("Server Down");
        incident.setStatus("Open");

        when(incidentRepository.save(incident)).thenReturn(incident);

        Incident result = incidentService.createIncident(incident);

        assertEquals("Server Down", result.getTitle());
        assertEquals("Open", result.getStatus());
    }

    @Test
    void testGetIncidentById() {
        Incident incident = new Incident();
        incident.setId(1L);
        incident.setTitle("Login Issue");

        when(incidentRepository.findById(1L)).thenReturn(Optional.of(incident));

        Incident result = incidentService.getIncidentById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Login Issue", result.getTitle());
    }

    @Test
    void testUpdateIncident() {
        Incident oldIncident = new Incident();
        oldIncident.setId(1L);
        oldIncident.setTitle("Old Title");
        oldIncident.setStatus("Open");

        Incident updatedIncident = new Incident();
        updatedIncident.setTitle("New Title");
        updatedIncident.setStatus("Closed");
        updatedIncident.setDescription("Updated description");
        updatedIncident.setSeverity("High");

        when(incidentRepository.findById(1L)).thenReturn(Optional.of(oldIncident));
        when(incidentRepository.save(oldIncident)).thenReturn(oldIncident);

        Incident result = incidentService.updateIncident(1L, updatedIncident);

        assertEquals("New Title", result.getTitle());
        assertEquals("Closed", result.getStatus());
        assertEquals("High", result.getSeverity());
    }
}