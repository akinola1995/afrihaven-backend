package com.caremyhome.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Document {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;


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