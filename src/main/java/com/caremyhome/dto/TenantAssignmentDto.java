package com.caremyhome.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TenantAssignmentDto {
    private String email;
    private String agentEmail;
    private String unit;
    private UUID propertyId;
    private LocalDateTime assignedAt;

    public TenantAssignmentDto(String tenantEmail, String propertyId, LocalDateTime assignedAt) {
    }
}

