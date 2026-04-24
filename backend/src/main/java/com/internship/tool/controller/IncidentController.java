package com.internship.tool.controller;

import com.internship.tool.entity.Incident;
import com.internship.tool.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@CrossOrigin(origins = "*")
public class IncidentController {

 @Autowired
 private IncidentRepository repo;

 // ✅ CREATE
@PostMapping
public Incident create(@RequestBody Incident incident) {
 incident.setIncidentDate(java.time.LocalDateTime.now()); // ✅ ADD THIS
 return repo.save(incident);
}

 // ✅ GET ALL WITH PAGINATION (DAY 5 IMPORTANT)
 @GetMapping("/all")
 public Page<Incident> getAll(Pageable pageable) {
  return repo.findAll(pageable);
 }

 // ✅ SEARCH
 @GetMapping("/search")
 public List<Incident> search(@RequestParam String q) {
  return repo.findByTitleContainingIgnoreCase(q);
 }

 // ✅ UPDATE
 @PutMapping("/{id}")
 public Incident update(@PathVariable Long id, @RequestBody Incident updated) {
  Incident existing = repo.findById(id).orElseThrow();

  existing.setTitle(updated.getTitle());
  existing.setDescription(updated.getDescription());
  existing.setSeverity(updated.getSeverity());
  existing.setStatus(updated.getStatus());

  return repo.save(existing);
 }

 // ✅ SOFT DELETE
 @DeleteMapping("/{id}")
 public void delete(@PathVariable Long id) {
  Incident incident = repo.findById(id).orElseThrow();
  incident.setIsDeleted(true);
  repo.save(incident);
 }
}