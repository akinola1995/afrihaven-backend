package com.caremyhome.service;

import com.caremyhome.dto.OwnerDashboardResponseDTO;
import com.caremyhome.model.*;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OwnerDashboardService {
    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private RentUploadRepository rentUploadRepo;
    @Autowired private PropertyTenantAssignmentRepository assignmentRepo;
    @Autowired private UnassignmentRequestRepository unassignmentRequestRepo;

    public OwnerDashboardResponseDTO getOwnerDashboard(String ownerEmail) {
        User owner = userRepo.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        if (owner.getRole() != User.Role.OWNER) {
            throw new RuntimeException("User is not an owner");
        }

        // Fetch all properties for this owner
        List<Property> properties = propertyRepo.findByOwner(owner);

        // Map property details
        List<Map<String, Object>> propertyList = properties.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("title", p.getTitle());
            map.put("type", p.getType());  // <-- Ensure 'type' is correct field in Property
            return map;
        }).collect(Collectors.toList());

        // Aggregate inquiries for all properties
        List<Map<String, Object>> inquiries = properties.stream()
                .flatMap(p -> p.getInquiries() != null ? p.getInquiries().stream() : Stream.empty())
                .map(inq -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inq.getId());
                    map.put("message", inq.getMessage());
                    map.put("createdAt", inq.getCreatedAt());
                    map.put("propertyId", inq.getProperty().getId());
                    map.put("status", inq.getStatus());
                    return map;
                }).collect(Collectors.toList());

        // Aggregate maintenance requests
        List<Map<String, Object>> maintenance = properties.stream()
                .flatMap(p -> p.getMaintenanceRequests() != null ? p.getMaintenanceRequests().stream() : Stream.empty())
                .map(m -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("issue", m.getIssue());
                    map.put("status", m.getStatus());
                    map.put("propertyId", m.getProperty().getId());
                    map.put("date", m.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());

        // Aggregate rent uploads
        List<Map<String, Object>> rentUploads = properties.stream()
                .flatMap(p -> p.getRentUploads() != null ? p.getRentUploads().stream() : Stream.empty())
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("tenant", r.getTenant() != null ? r.getTenant() : ""); // if tenant is String
                    map.put("file", r.getFileName());
                    map.put("amount", r.getAmount());
                    map.put("date", r.getUploadedAt());
                    return map;
                }).collect(Collectors.toList());

        // Aggregate tenant assignments
        List<Map<String, Object>> tenantAssignments = assignmentRepo
                .findByPropertyOwnerAndStatus(owner, "Active").stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", a.getTenant().getEmail());
                    map.put("propertyId", a.getProperty().getId());
                    map.put("assignedAt", a.getAssignedAt());
                    return map;
                }).collect(Collectors.toList());

        // Aggregate unassignment requests
        List<Map<String, Object>> unassignmentRequests = unassignmentRequestRepo
                .findByOwner(owner).stream()
                .map(req -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", req.getTenant().getEmail());
                    map.put("propertyId", req.getProperty().getId());
                    map.put("requestedAt", req.getRequestedAt());
                    return map;
                }).collect(Collectors.toList());

        OwnerDashboardResponseDTO dto = new OwnerDashboardResponseDTO();
        dto.setName(owner.getName());
        dto.setProperties(propertyList);
        dto.setInquiries(inquiries);
        dto.setMaintenance(maintenance);
        dto.setRentUploads(rentUploads);
        dto.setTenantAssignments(tenantAssignments);
        dto.setUnassignmentRequests(unassignmentRequests);
        return dto;
    }

    public void assignTenant(String tenantEmail, UUID propertyId) {
        User tenant = userRepo.findByEmail(tenantEmail)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        // Prevent duplicate assignment
        boolean exists = assignmentRepo.existsByTenantAndPropertyAndStatus(tenant, property, "Active");
        if (exists) {
            throw new RuntimeException("Tenant already assigned to this property");
        }
        PropertyTenantAssignment assignment = new PropertyTenantAssignment();
        assignment.setTenant(tenant);
        assignment.setAssignedBy(property.getOwner());
        assignment.setProperty(property);
        assignment.setUnit(""); // optional
        assignment.setStatus("Active");
        assignment.setAssignedAt(java.time.LocalDateTime.now());
        assignmentRepo.save(assignment);
    }
}
