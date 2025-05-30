package com.caremyhome.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false)
    private String from; // Email

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private String status; // e.g., "Open", "Closed", "In Progress"

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}