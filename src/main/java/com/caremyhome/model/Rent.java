package com.caremyhome.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tenantEmail;
    private String tenant;

    private double amount;

    private LocalDate dueDate;

    private String status;

    @ManyToOne
    private Property property;

    // Getters and Setters
}

