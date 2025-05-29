package com.caremyhome.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private List<MaintenanceComment> comments = new ArrayList<>();

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
        this.status = "Pending";
    }

    // Getters and Setters
}