package com.caremyhome.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inquiries")
@Data
@NoArgsConstructor
public class Inquiry {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "from_user", nullable = false)
    private String fromUser;

    @Column(nullable = false)
    private String message;

    // NEW: Add Name (not nullable, since your UI requires it)
    @Column(nullable = false)
    private String name;

    // NEW: Add Phone (optional, so nullable)
    @Column
    private String phone;

    @Column(nullable = false)
    private String status; // e.g., "Open", "Closed", "In Progress", "Replied"

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Instant date;

    private String email;
}
