package com.caremyhome.model;

import com.caremyhome.dto.TenantAssignmentDto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

import lombok.Data;

@Data
@Builder
@Getter
@Setter
@Entity
public class TenantAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    private String unit;
    private String tenantEmail;
    private String agentEmail;

    private String propertyId;
    private boolean active = true;

    private LocalDateTime assignedAt = LocalDateTime.now();

    public TenantAssignment(TenantAssignmentDto dto) {
        this.email = dto.getEmail();
        this.agentEmail = dto.getAgentEmail(); // set from DTO
        this.propertyId = dto.getPropertyId().toString();
    }

    public TenantAssignment() {}

    @ManyToOne
    private Property property;
}

    // Getters & Setters

