package com.caremyhome.service;

@Service
public class AgentService {

    @Autowired private PropertyRepository propertyRepo;
    @Autowired private TenantAssignmentRepository assignmentRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private AgentRepository agentRepo;

    public Map<String, Object> getAgentDashboard(String email) {
        Map<String, Object> data = new HashMap<>();
        List<Property> properties = propertyRepo.findByAgentEmail(email);
        data.put("properties", properties);
        data.put("assignedTenants", assignmentRepo.findActiveByAgentEmail(email));
        data.put("assignmentHistory", assignmentRepo.findHistoryByAgentEmail(email));
        data.put("inquiries", inquiryRepo.findByAgentEmail(email));
        data.put("maintenance", maintenanceRepo.findByAgentEmail(email));
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

