package com.caremyhome.model;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "rent_uploads")
@Data
@NoArgsConstructor
public class RentUpload {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
