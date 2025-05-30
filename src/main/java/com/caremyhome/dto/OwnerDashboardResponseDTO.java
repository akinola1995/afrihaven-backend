package com.caremyhome.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class OwnerDashboardResponseDTO {
    private String name;
    private List<Map<String, Object>> properties;
    private List<Map<String, Object>> inquiries;
    private List<Map<String, Object>> maintenance;
    private List<Map<String, Object>> rentUploads;
    private List<Map<String, Object>> tenantAssignments;
    private List<Map<String, Object>> unassignmentRequests;
}
