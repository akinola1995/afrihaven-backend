package com.caremyhome.model;

import com.caremyhome.dto.TenantAssignmentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TenantAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    @ManyToOne
    @JoinColumn(name = "owner_id", columnDefinition = "uuid")
    private User owner;

    private String unit;
    private String tenantEmail;
    private String agentEmail;

    private boolean active = true;
    private LocalDateTime assignedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "property_id", referencedColumnName = "id")
    private Property property;

    // DTO constructor (optional)
    public TenantAssignment(TenantAssignmentDto dto) {
        this.email = dto.getEmail();
        this.agentEmail = dto.getAgentEmail();
        // Assign property if needed, e.g., using propertyRepo.findById(dto.getPropertyId())
        // (Don't set propertyId as a separate field!)
    }
}
