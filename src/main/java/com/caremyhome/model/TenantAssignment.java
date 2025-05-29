package com.caremyhome.model;

import com.caremyhome.dto.TenantAssignmentDto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
}

    // Getters & Setters

