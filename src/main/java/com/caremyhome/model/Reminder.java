package com.caremyhome.model;

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

