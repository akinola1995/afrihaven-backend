package com.caremyhome.controller;

import com.caremyhome.dto.TenantDTO;
import com.caremyhome.model.*;
import com.caremyhome.repository.*;
import com.caremyhome.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TenantController {

    @Autowired
    private TenantService tenantDashboardService;
    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private PropertyTenantAssignmentRepository assignmentRepo;

    // GET /api/tenant/{email}
    @GetMapping("/api/tenant/{email}")
    public Map<String, Object> getTenantDashboard(@PathVariable String email) {
        return tenantDashboardService.getTenantDashboard(email);
    }

    // GET /api/documents/tenant/{email}
    @GetMapping("/api/documents/tenant/{email}")
    public List<Map<String, Object>> getTenantDocuments(@PathVariable String email) {
        return tenantDashboardService.getTenantDocuments(email);
    }

    @PostMapping("/api/tenant/assign")
    public ResponseEntity<?> assignTenantToProperty(@RequestBody Map<String, String> req) {
        // Required payload: { "propertyId": "...", "tenantEmail": "...", "unit": "..." }
        String propertyId = req.get("propertyId");
        String tenantEmail = req.get("tenantEmail");
        String unit = req.getOrDefault("unit", null);

        if (propertyId == null || tenantEmail == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "propertyId and tenantEmail required"));
        }

        Optional<Property> propOpt = propertyRepo.findById(UUID.fromString(propertyId));
        Optional<User> userOpt = userRepo.findByEmail(tenantEmail);

        if (propOpt.isEmpty() || userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Property or Tenant not found"));
        }

        Property property = propOpt.get();
        User tenant = userOpt.get();

        // Prevent duplicate active assignment
        if (assignmentRepo.existsByTenantAndPropertyAndStatus(tenant, property, "Active")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tenant already assigned to this property"));
        }

        PropertyTenantAssignment assignment = new PropertyTenantAssignment();
        assignment.setTenant(tenant);
        assignment.setProperty(property);
        assignment.setStatus("Active");
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setUnit(unit);

        assignmentRepo.save(assignment);

        return ResponseEntity.ok(Map.of("message", "Tenant assigned successfully"));
    }
}


