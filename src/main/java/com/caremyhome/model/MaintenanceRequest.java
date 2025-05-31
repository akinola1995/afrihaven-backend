package com.caremyhome.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Entity
@Table(name = "maintenance_requests")
@Data
@NoArgsConstructor
public class MaintenanceRequest {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID propertyId;

    @Column(nullable = false)
    private String tenantEmail;

    @Column(nullable = false)
    private String tenantName;

    @Column(nullable = false)
    private String issue;

    @Column(nullable = false)
    private String urgency;

    @Column(nullable = false)
    private String status = "Pending";

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "maintenance_comments", joinColumns = @JoinColumn(name = "maintenance_id"))
    private List<Comment> comments = new ArrayList<>();

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class Comment {
        @Column(nullable = false)
        private String from;  // role or user
        @Column(nullable = false, columnDefinition = "TEXT")
        private String text;
        @Column(nullable = false)
        private Instant date = Instant.now();
    }
}