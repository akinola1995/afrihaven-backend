package com.caremyhome.service;

import com.caremyhome.dto.TenantAssignmentDto;
import com.caremyhome.model.*;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import lombok.Builder;
import com.caremyhome.dto.AgentDashboardResponseDTO;

@Service
public class AgentService {

    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private PropertyTenantAssignmentRepository assignmentRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;

    public AgentDashboardResponseDTO getDashboard(String agentEmail) {
        User agent = userRepo.findByEmail(agentEmail);
        if (agent == null || agent.getRole() != User.Role.AGENT) {
            throw new RuntimeException("Not authorized or not an agent");
        }

        List<Property> properties = propertyRepo.findByAssignedAgent(agent);

        List<PropertyTenantAssignment> activeAssignments = assignmentRepo
                .findByPropertyAssignedAgentAndStatus(agent, "Active");

        List<PropertyTenantAssignment> historyAssignments = assignmentRepo
                .findByPropertyAssignedAgent(agent);

        // Agents added by this agent
        List<User> agentsAdded = userRepo.findByRoleAndRegisteredBy(User.Role.AGENT, agent);

        // All inquiries and maintenance for agent's properties
        List<Inquiry> inquiries = new ArrayList<>();
        List<MaintenanceRequest> maintenance = new ArrayList<>();
        for (Property property : properties) {
            if (property.getInquiries() != null) inquiries.addAll(property.getInquiries());
            if (property.getMaintenanceRequests() != null) maintenance.addAll(property.getMaintenanceRequests());
        }

        // DTO mapping
        AgentDashboardResponseDTO dto = new AgentDashboardResponseDTO();

        dto.setProperties(
                properties.stream().map(p -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", p.getId());
                    m.put("title", p.getTitle());
                    m.put("address", p.getAddress());
                    m.put("status", p.getStatus());
                    m.put("price", p.getPrice());
                    return m;
                }).collect(Collectors.toList())
        );

        dto.setAssignedTenants(
                activeAssignments.stream().map(a -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("email", a.getAssignedTenant().getEmail());
                    m.put("name", a.getAssignedTenant().getName());
                    m.put("unit", a.getUnit());
                    m.put("propertyId", a.getProperty().getId());
                    m.put("propertyTitle", a.getProperty().getTitle());
                    return m;
                }).collect(Collectors.toList())
        );

        dto.setAssignmentHistory(
                historyAssignments.stream().map(a -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("email", a.getAssignedTenant().getEmail());
                    m.put("name", a.getAssignedTenant().getName());
                    m.put("unit", a.getUnit());
                    m.put("propertyId", a.getProperty().getId());
                    m.put("propertyTitle", a.getProperty().getTitle());
                    m.put("status", a.getStatus());
                    m.put("assignedAt", a.getAssignedAt());
                    return m;
                }).collect(Collectors.toList())
        );

        dto.setAgentList(
                agentsAdded.stream().map(a -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("email", a.getEmail());
                    m.put("name", a.getName());
                    m.put("createdAt", a.getCreatedAt());
                    return m;
                }).collect(Collectors.toList())
        );

        dto.setInquiries(
                inquiries.stream().map(inq -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", inq.getId());
                    m.put("from", inq.getFrom());
                    m.put("message", inq.getMessage());
                    m.put("propertyId", inq.getProperty() != null ? inq.getProperty().getId() : null);
                    m.put("date", inq.getDate());
                    return m;
                }).collect(Collectors.toList())
        );

        dto.setMaintenance(
                maintenance.stream().map(mr -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", mr.getId());
                    m.put("issue", mr.getIssue());
                    m.put("status", mr.getStatus());
                    m.put("propertyId", mr.getProperty() != null ? mr.getProperty().getId() : null);
                    m.put("date", mr.getDate());
                    return m;
                }).collect(Collectors.toList())
        );

        return dto;
    }

    public void assignTenant(String agentEmail, String tenantEmail, String unit, UUID propertyId) {
        User agent = userRepo.findByEmail(agentEmail);
        User tenant = userRepo.findByEmail(tenantEmail);
        Property property = propertyRepo.findById(propertyId).orElse(null);
        if (agent == null || tenant == null || property == null) throw new RuntimeException("Invalid agent/tenant/property");
        if (property.getAssignedAgent() == null || !property.getAssignedAgent().getEmail().equals(agentEmail))
            throw new RuntimeException("Agent not assigned to this property");

        // Create assignment
        PropertyTenantAssignment assignment = new PropertyTenantAssignment();
        assignment.setAssignedTenant(tenant);
        assignment.setAssignedBy(agent);
        assignment.setProperty(property);
        assignment.setUnit(unit);
        assignment.setStatus("Active");
        assignment.setAssignedAt(java.time.LocalDateTime.now());
        assignmentRepo.save(assignment);
    }

    public void unassignTenant(String agentEmail, String tenantEmail) {
        User agent = userRepo.findByEmail(agentEmail);
        User tenant = userRepo.findByEmail(tenantEmail);
        List<PropertyTenantAssignment> assignments = assignmentRepo.findByAssignedTenant(tenant);
        for (PropertyTenantAssignment a : assignments) {
            if (a.getAssignedBy().equals(agent) && a.getStatus().equals("Active")) {
                a.setStatus("Unassigned");
                assignmentRepo.save(a);
            }
        }
    }

    public void addAgent(String name, String email, String registeredByEmail) {
        if (userRepo.existsByEmail(email)) throw new RuntimeException("Email already exists");
        User registeredBy = userRepo.findByEmail(registeredByEmail);
        if (registeredBy == null) throw new RuntimeException("Registering user not found");
        User newAgent = new User();
        newAgent.setName(name);
        newAgent.setEmail(email);
        newAgent.setRole(User.Role.AGENT);
        newAgent.setRegisteredBy(registeredBy);
        newAgent.setCreatedAt(java.time.LocalDateTime.now());
        userRepo.save(newAgent);
    }
}