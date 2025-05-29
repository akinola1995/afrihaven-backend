package com.caremyhome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String date; // Stored as String (e.g. 2025-05-12)
    private String type; // Rent, Inspection, Maintenance
    private String email;

    // Getters and Setters
}

