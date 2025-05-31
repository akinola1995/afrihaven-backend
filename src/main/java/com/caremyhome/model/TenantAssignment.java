//package com.caremyhome.model;
//
//import com.caremyhome.dto.TenantAssignmentDto;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Data
//@Builder
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//public class TenantAssignment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    private String email;
//
//    @ManyToOne
//    @JoinColumn(name = "owner_id", referencedColumnName = "id")
//    private User owner;
//    private String unit;
//    private String tenantEmail;
//    private String agentEmail;
//
//    private String propertyId;
//    private boolean active = true;
//
//    private LocalDateTime assignedAt = LocalDateTime.now();
//
//    public TenantAssignment(TenantAssignmentDto dto) {
//        this.email = dto.getEmail();
//        this.agentEmail = dto.getAgentEmail(); // set from DTO
//        this.propertyId = dto.getPropertyId().toString();
//    }
//
//    public TenantAssignment() {}
//
//    @ManyToOne
//    private Property property;
//}
//
//    // Getters & Setters
//

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
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    private String unit;
    private String tenantEmail;
    private String agentEmail;
    private String propertyId;
    private boolean active = true;
    private LocalDateTime assignedAt = LocalDateTime.now();

    @ManyToOne
    private Property property;

    // DTO constructor (optional, not required for JPA)
    public TenantAssignment(TenantAssignmentDto dto) {
        this.email = dto.getEmail();
        this.agentEmail = dto.getAgentEmail();
        this.propertyId = dto.getPropertyId().toString();
    }
}

