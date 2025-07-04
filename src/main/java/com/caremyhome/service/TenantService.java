package com.caremyhome.service;

import com.caremyhome.dto.TenantDTO;
import com.caremyhome.dto.TenantViewDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.PropertyTenantAssignment;
import com.caremyhome.model.Tenant;
import com.caremyhome.model.User;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TenantService {
    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private DocumentRepository documentRepo;

    @Autowired
    private PropertyTenantAssignmentRepository assignmentRepo;


    public Map<String, Object> getTenantDashboard(String email) {
        Optional<User> tenantOpt = userRepo.findByEmail(email);
        if (tenantOpt.isEmpty()) return null;

        User tenant = tenantOpt.get();

        PropertyTenantAssignment assignment = assignmentRepo
                .findFirstByTenantAndStatusOrderByAssignedAtDesc(tenant, "Active")
                .orElse(null);

        Property property = assignment != null ? assignment.getProperty() : null;

        List<Map<String, Object>> maintenance = maintenanceRepo
                .findByTenantEmailOrderByCreatedAtDesc(email)
                .stream().map(req -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("issue", req.getIssue());
                    map.put("status", req.getStatus());
                    map.put("date", req.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("name", tenant.getName());

        if (property != null) {
            Map<String, Object> propMap = new HashMap<>();
            propMap.put("id", property.getId());
            propMap.put("title", property.getTitle());
            propMap.put("unit", assignment != null ? assignment.getUnit() : null);
            String city = property.getCity() != null ? property.getCity() : "";
            String state = property.getState() != null ? property.getState() : "";
            propMap.put("location", city + (state.isEmpty() ? "" : ", " + state));
            propMap.put("rent", property.getRent());
            propMap.put("dueDate", property.getDueDate());
            result.put("property", propMap);
        } else {
            result.put("property", null);
        }
        result.put("maintenanceRequests", maintenance);

        return result;
    }

    public List<Map<String, Object>> getTenantDocuments(String email) {
        return documentRepo.findByTenantEmailOrderByUploadedAtDesc(email).stream()
                .map(doc -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", doc.getName());
                    map.put("description", doc.getDescription());
                    map.put("uploadedAt", doc.getUploadedAt());
                    map.put("url", doc.getUrl());
                    return map;
                }).collect(Collectors.toList());
    }



    public List<TenantViewDTO> getTenantsForProperty(UUID propertyId) {
        Optional<Property> propertyOpt = propertyRepo.findById(propertyId);
        if (propertyOpt.isEmpty()) return Collections.emptyList();

        List<PropertyTenantAssignment> assignments = assignmentRepo.findByProperty(propertyOpt.get());
        return assignments.stream().map(assignment -> {
            User tenant = assignment.getTenant();
            String rentStatus = assignment.getRentStatus(); // or calculate it
            return new TenantViewDTO(
                    tenant.getName(),
                    tenant.getEmail(),
                    assignment.getUnit(),
                    tenant.getPhone(),
                    rentStatus
            );
        }).collect(Collectors.toList());
    }

}