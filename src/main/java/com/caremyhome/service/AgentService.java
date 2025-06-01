package com.caremyhome.service;

import com.caremyhome.dto.AgentDashboardResponseDTO;
import com.caremyhome.model.*;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AgentService {
    @Autowired
    private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private PropertyTenantAssignmentRepository assignmentRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;

    public AgentDashboardResponseDTO getDashboard(String agentEmail) {
        Optional<User> agentOpt = userRepo.findByEmail(agentEmail);

        if (agentOpt.isEmpty() || agentOpt.get().getRole() != User.Role.AGENT) {
            throw new RuntimeException("Not authorized or not an agent");
        }

        User agent = agentOpt.get();

        List<Property> properties = propertyRepo.findByAssignedAgent(agent);
        List<PropertyTenantAssignment> activeAssignments = assignmentRepo.findByPropertyAssignedAgentAndStatus(agent, "Active");
        List<PropertyTenantAssignment> historyAssignments = assignmentRepo.findByPropertyAssignedAgent(agent);
        List<User> agentsAdded = userRepo.findByRoleAndRegisteredBy(User.Role.AGENT, agent);

        List<Inquiry> inquiries = new ArrayList<>();
        List<MaintenanceRequest> maintenance = new ArrayList<>();
        for (Property property : properties) {
            if (property.getInquiries() != null) inquiries.addAll(property.getInquiries());
            if (property.getMaintenanceRequests() != null) maintenance.addAll(property.getMaintenanceRequests());
        }

        AgentDashboardResponseDTO dto = new AgentDashboardResponseDTO();
        // ... build your DTO as you have it ...
        return dto;
    }

    public void assignTenant(String agentEmail, String tenantEmail, String unit, UUID propertyId) {
        Optional<User> agentOpt = userRepo.findByEmail(agentEmail);
        Optional<User> tenantOpt = userRepo.findByEmail(tenantEmail);
        Optional<Property> propertyOpt = propertyRepo.findById(propertyId);

        if (agentOpt.isEmpty() || tenantOpt.isEmpty() || propertyOpt.isEmpty()) throw new RuntimeException("Invalid agent/tenant/property");
        User agent = agentOpt.get();
        User tenant = tenantOpt.get();
        Property property = propertyOpt.get();

        if (property.getAssignedAgent() == null || !property.getAssignedAgent().getEmail().equals(agentEmail))
            throw new RuntimeException("Agent not assigned to this property");

        PropertyTenantAssignment assignment = new PropertyTenantAssignment();
        assignment.setTenant(tenant);
        assignment.setAssignedBy(agent);
        assignment.setProperty(property);
        assignment.setUnit(unit);
        assignment.setStatus("Active");
        assignment.setAssignedAt(java.time.LocalDateTime.now());
        assignmentRepo.save(assignment);
    }

    public void unassignTenant(String agentEmail, String tenantEmail) {
        Optional<User> agentOpt = userRepo.findByEmail(agentEmail);
        Optional<User> tenantOpt = userRepo.findByEmail(tenantEmail);
        if (agentOpt.isEmpty() || tenantOpt.isEmpty()) throw new RuntimeException("Not found");
        User agent = agentOpt.get();
        User tenant = tenantOpt.get();

        List<PropertyTenantAssignment> assignments = assignmentRepo.findByTenant(tenant);
        for (PropertyTenantAssignment a : assignments) {
            if (a.getAssignedBy().equals(agent) && a.getStatus().equals("Active")) {
                a.setStatus("Unassigned");
                assignmentRepo.save(a);
            }
        }
    }

    public void addAgent(String name, String email, String registeredByEmail) {
        if (userRepo.existsByEmail(email)) throw new RuntimeException("Email already exists");
        Optional<User> registeredByOpt = userRepo.findByEmail(registeredByEmail);
        if (registeredByOpt.isEmpty()) throw new RuntimeException("Registering user not found");
        User registeredBy = registeredByOpt.get();

        User newAgent = new User();
        newAgent.setName(name);
        newAgent.setEmail(email);
        newAgent.setRole(User.Role.AGENT);
        newAgent.setRegisteredBy(registeredBy);
        newAgent.setCreatedAt(java.time.LocalDateTime.now());
        userRepo.save(newAgent);
    }
}
