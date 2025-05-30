package com.caremyhome.service;

import com.caremyhome.dto.OwnerDashboardResponseDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.PropertyTenantAssignment;
import com.caremyhome.model.RentUpload;
import com.caremyhome.model.User;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        User owner = userRepo.findByEmail(ownerEmail);
        if (owner == null || owner.getRole() != User.Role.OWNER) {
            throw new RuntimeException("Owner not found or not an owner");
        }

        List<Property> properties = propertyRepo.findByOwner(owner);

        // Properties summary
        List<Map<String, Object>> propertyList = properties.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("title", p.getTitle());
            map.put("type", p.getStatus());
            return map;
        }).collect(Collectors.toList());

        // All inquiries for owner's properties
        List<Map<String, Object>> inquiries = properties.stream()
                .flatMap(p -> p.getInquiries().stream())
                .map(inq -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", inq.getId());
                    map.put("message", inq.getMessage());
                    map.put("createdAt", inq.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());

        // All maintenance requests for owner's properties
        List<Map<String, Object>> maintenance = properties.stream()
                .flatMap(p -> p.getMaintenanceRequests().stream())
                .map(m -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("issue", m.getIssue());
                    map.put("status", m.getStatus());
                    map.put("propertyId", m.getProperty().getId());
                    map.put("date", m.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());

        // Rent uploads (optional feature; customize as needed)
        List<Map<String, Object>> rentUploads = properties.stream()
                .flatMap(p -> {
                    if (p.getRentUploads() != null) {
                        return p.getRentUploads().stream();
                    } else {
                        return new ArrayList<RentUpload>().stream();
                    }
                })
                .map(r -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("tenant", r.getTenant() != null ? r.getTenant().getEmail() : "");
                    map.put("file", r.getFileName());
                    map.put("amount", r.getAmount());
                    map.put("date", r.getUploadedAt());
                    return map;
                }).collect(Collectors.toList());

        // Tenant assignments (active assignments)
        List<Map<String, Object>> tenantAssignments = assignmentRepo
                .findByPropertyOwnerAndStatus(owner, "Active").stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", a.getAssignedTenant().getEmail());
                    map.put("propertyId", a.getProperty().getId());
                    map.put("assignedAt", a.getAssignedAt());
                    return map;
                }).collect(Collectors.toList());

        // Unassignment requests
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
        User tenant = userRepo.findByEmail(tenantEmail);
        Property property = propertyRepo.findById(propertyId).orElse(null);
        if (tenant == null || property == null) throw new RuntimeException("Invalid tenant or property");

        // You might want to prevent duplicate assignments here
        PropertyTenantAssignment assignment = new PropertyTenantAssignment();
        assignment.setAssignedTenant(tenant);
        assignment.setAssignedBy(property.getOwner());
        assignment.setProperty(property);
        assignment.setUnit(""); // fill if applicable
        assignment.setStatus("Active");
        assignment.setAssignedAt(java.time.LocalDateTime.now());
        assignmentRepo.save(assignment);
    }
}
