package com.internship.tool.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String severity;

    private String status;

    private LocalDateTime incidentDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}