package com.internship.tool.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String title;
 private String description;
 private String severity;
 private String status;

 private LocalDateTime incidentDate;

 private Boolean isDeleted = false;

 public Long getId() { return id; }
 public void setId(Long id) { this.id = id; }

 public String getTitle() { return title; }
 public void setTitle(String title) { this.title = title; }

 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }

 public String getSeverity() { return severity; }
 public void setSeverity(String severity) { this.severity = severity; }

 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }

 public LocalDateTime getIncidentDate() { return incidentDate; }
 public void setIncidentDate(LocalDateTime incidentDate) { this.incidentDate = incidentDate; }

 public Boolean getIsDeleted() { return isDeleted; }
 public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}