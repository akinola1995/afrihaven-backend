package com.caremyhome.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnassignmentRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id", columnDefinition = "uuid")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id", columnDefinition = "uuid")
    private User tenant;

    @ManyToOne
    private Property property; // << This is all you need!

    private String reason;
    private String tenantEmail;
    // REMOVE this line: private String propertyId;
    private LocalDateTime requestedAt;
}
