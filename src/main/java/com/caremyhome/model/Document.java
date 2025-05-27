package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Document {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String url;
    private String description;

    private LocalDateTime uploadedAt;

    @PrePersist
    public void setTimestamp() {
        this.uploadedAt = LocalDateTime.now();
    }

    private String filePath;

    @ManyToOne
    private User uploadedBy;

    @ManyToOne
    private Property property;

    // Getters and setters
}