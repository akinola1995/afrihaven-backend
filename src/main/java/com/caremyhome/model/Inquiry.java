package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String message;

    private String email;

    private LocalDateTime createdAt;

    @ManyToOne
    private Property property;

    @PrePersist
    public void setTimestamp() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    private String name;
    private String phone;

    private LocalDateTime submittedAt;


    @PrePersist
    public void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }

}
