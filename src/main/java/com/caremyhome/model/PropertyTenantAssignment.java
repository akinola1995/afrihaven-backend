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
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;
    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    private User assignedBy;
    private String rentStatus;

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User assignedTenant;

    // Optionally: unit assigned (if needed per property)
    private String unit;
    private String status;
}
