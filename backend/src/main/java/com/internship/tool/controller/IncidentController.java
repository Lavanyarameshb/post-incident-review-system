package com.internship.tool.controller;

import com.internship.tool.entity.Incident;
import com.internship.tool.service.IncidentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

 private final IncidentService service;

 public IncidentController(IncidentService service){ this.service = service; }

 @GetMapping("/all")
 public List<Incident> all(){ return service.findAll(); }

 @PutMapping("/{id}")
 public Incident update(@PathVariable Long id, @RequestBody Incident body){
  return service.update(id, body);
 }

 @DeleteMapping("/{id}")
 public void softDelete(@PathVariable Long id){
  service.softDelete(id);
 }

 @GetMapping("/search")
 public List<Incident> search(@RequestParam String q){
  return service.search(q);
 }
}