package com.caremyhome.model;

public class TenantAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String propertyId;

    private LocalDateTime assignedAt = LocalDateTime.now();

    // Getters & Setters
}
