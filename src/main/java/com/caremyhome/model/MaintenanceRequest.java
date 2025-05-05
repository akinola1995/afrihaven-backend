package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class MaintenanceRequest {
    @Id
    @GeneratedValue
    private UUID id;

    private String issue;
    private String status; // Pending, Resolved
    private String urgency; // Optional: Low, Medium, High
    private LocalDate createdAt;

    @ManyToOne
    private Property property;

    @ManyToOne
    private User createdBy;

    // Getters and setters
}