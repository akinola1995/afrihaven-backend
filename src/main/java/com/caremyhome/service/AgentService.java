package com.caremyhome.service;

import com.caremyhome.dto.TenantAssignmentDto;
import com.caremyhome.model.Property;
import com.caremyhome.model.TenantAssignment;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentService {

    @Autowired
    private PropertyRepository propertyRepo;
    @Autowired private TenantAssignmentRepository assignmentRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private AgentRepository agentRepo;

    public Map<String, Object> getAgentDashboard(String email) {
        Map<String, Object> data = new HashMap<>();
        List<Property> properties = propertyRepo.findByAgent_Email(email);
        data.put("properties", properties);
        data.put("assignedTenants", assignmentRepo.findActiveByAgentEmail(email));
        data.put("assignmentHistory", assignmentRepo.findHistoryByAgentEmail(email));
        data.put("inquiries", inquiryRepo.findByAgent_Email(email));
        data.put("maintenance", maintenanceRepo.findByAgent_Email(email));
        data.put("agentList", agentRepo.findByRegisteredBy(email));
        return data;
    }

    public void assignTenant(TenantAssignmentDto dto) {
        // Save assignment and update history
        assignmentRepo.save(new TenantAssignment(dto));
    }

    public void unassignTenant(String agentEmail, String tenantEmail) {
        assignmentRepo.deactivate(agentEmail, tenantEmail);
    }
}

