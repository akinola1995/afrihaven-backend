package com.caremyhome.service;

import com.caremyhome.dto.TenantDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.Tenant;
import com.caremyhome.model.User;
import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TenantService {
    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private DocumentRepository documentRepo;

    public Map<String, Object> getTenantDashboard(String email) {
        User tenant = userRepo.findByEmail(email);
        if (tenant == null) return null;

        // Find property for this tenant (adjust logic if 1:1 is not guaranteed)
        Property property = propertyRepo.findByTenantEmail(email);

        // Maintenance requests
        List<Map<String, Object>> maintenance = maintenanceRepo.findByTenantEmailOrderByCreatedAtDesc(email)
                .stream().map(req -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("issue", req.getIssue());
                    map.put("status", req.getStatus());
                    map.put("date", req.getCreatedAt());
                    return map;
                }).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("name", tenant.getName());
        result.put("property", Map.of(
                "id", property.getId(),
                "title", property.getTitle(),
                "unit", property.getUnit(),
                "location", property.getLocation(),
                "rent", property.getRent(),
                "dueDate", property.getDueDate()
        ));
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
}