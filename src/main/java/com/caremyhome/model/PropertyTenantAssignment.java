package com.caremyhome.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "property_tenant_assignment")
@Data
@NoArgsConstructor
public class PropertyTenantAssignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne @JoinColumn(name = "tenant_id")
    private User assignedTenant;

    @Column(nullable = false)
    private String unit; // e.g., apartment/unit info

    @Column(nullable = false)
    private String status; // e.g., "Active", "Inactive", "Unassigned"

    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();

    @ManyToOne @JoinColumn(name = "assigned_by")
    private User assignedBy; // Agent or Owner
}
