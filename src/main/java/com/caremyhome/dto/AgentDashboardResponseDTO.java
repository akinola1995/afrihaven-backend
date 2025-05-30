package com.caremyhome.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AgentDashboardResponseDTO {
    private List<Map<String, Object>> properties;
    private List<Map<String, Object>> assignedTenants;
    private List<Map<String, Object>> assignmentHistory;
    private List<Map<String, Object>> agentList;
    private List<Map<String, Object>> inquiries;
    private List<Map<String, Object>> maintenance;
}
