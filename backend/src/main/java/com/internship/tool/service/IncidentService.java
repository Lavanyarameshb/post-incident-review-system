package com.internship.tool.service;

import com.internship.tool.entity.Incident;
import com.internship.tool.repository.IncidentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IncidentService {

 private final IncidentRepository repo;

 public IncidentService(IncidentRepository repo){ this.repo = repo; }

 public List<Incident> findAll(){
  return repo.findByIsDeletedFalse();
 }

 public Incident update(Long id, Incident body){
  Incident i = repo.findById(id).orElseThrow();
  i.setTitle(body.getTitle());
  i.setDescription(body.getDescription());
  i.setSeverity(body.getSeverity());
  i.setStatus(body.getStatus());
  return repo.save(i);
 }

 public void softDelete(Long id){
  Incident i = repo.findById(id).orElseThrow();
  i.setIsDeleted(true);
  repo.save(i);
 }

 public List<Incident> search(String q){
  return repo.findByTitleContainingIgnoreCaseAndIsDeletedFalse(q);
 }
}