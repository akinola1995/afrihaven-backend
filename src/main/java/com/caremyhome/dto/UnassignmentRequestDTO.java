package com.caremyhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnassignmentRequestDTO {
    private String email;
    private String propertyId;
    private LocalDateTime requestedAt;
    private String tenantEmail;
}
