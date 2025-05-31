package com.caremyhome.model;

import jakarta.persistence.Column;

import java.time.LocalDate;
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

    @Column(nullable = false)
    private String tenant; // Tenant email or name

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private String status; // "Unpaid", "Paid", "Overdue"

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // Optionally, store receipt/document link
    private String fileUrl;

    // Optionally, timestamp of upload/payment
    @Column(nullable = false)
    private LocalDate createdAt = LocalDate.now();
}