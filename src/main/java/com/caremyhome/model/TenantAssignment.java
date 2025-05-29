package com.caremyhome.model;

import com.caremyhome.dto.TenantAssignmentDto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TenantAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String propertyId;

    private LocalDateTime assignedAt = LocalDateTime.now();

    public TenantAssignment(TenantAssignmentDto dto) {
    }

    // Getters & Setters
}
