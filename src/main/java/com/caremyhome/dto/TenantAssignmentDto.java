package com.caremyhome.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TenantAssignmentDto {
    private String email;
    private String unit;
    private UUID propertyId;
    private String agentEmail;
}

