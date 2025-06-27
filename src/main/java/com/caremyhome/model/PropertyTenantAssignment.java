package com.caremyhome.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "property_tenant_assignments")
@Data
@NoArgsConstructor
public class PropertyTenantAssignment {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id", columnDefinition = "uuid")
    private User tenant;  // <-- Only this!


    @ManyToOne
    @JoinColumn(name = "assigned_by_id", columnDefinition = "uuid")
    private User assignedBy;

    private String rentStatus;

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    // Optionally: unit assigned (if needed per property)
    private String unit;
    private String status;
}
