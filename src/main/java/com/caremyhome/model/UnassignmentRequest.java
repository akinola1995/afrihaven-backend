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
    private User owner;

    @ManyToOne
    private User tenant;

    @ManyToOne
    private Property property;

    private String reason;
    private String tenantEmail;
    private String propertyId;
    private LocalDateTime requestedAt;
}
