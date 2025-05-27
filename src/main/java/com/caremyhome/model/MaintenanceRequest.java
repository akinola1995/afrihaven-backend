package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tenant;
    private String tenantName;
    private String tenantEmail;
    private String property;
    private String issue;
    private String urgency;
    private String status;

    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "maintenance_comments", joinColumns = @JoinColumn(name = "request_id"))
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
        this.status = "Pending";
    }

    // Getters and Setters
}