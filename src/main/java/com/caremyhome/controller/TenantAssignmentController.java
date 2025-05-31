package com.caremyhome.controller;

import com.caremyhome.dto.TenantViewDTO;
import com.caremyhome.model.ManualTenant;
import com.caremyhome.model.PropertyTenantAssignment;
import com.caremyhome.model.Property;
import com.caremyhome.repository.ManualTenantRepository;
import com.caremyhome.repository.PropertyTenantAssignmentRepository;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.service.PropertyTenantService;
import com.caremyhome.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantAssignmentController {

    @Autowired
    private PropertyTenantService tenantService;

    @Autowired
    private TenantService tenantServices;

    // Assigned tenants (email/user)
    @GetMapping("/assignments/{propertyId}")
    public List<Map<String, Object>> getAssignments(@PathVariable UUID propertyId) {
        return tenantService.getAssignmentsByProperty(propertyId);
    }

    // Manual tenants
    @GetMapping("/manual/{propertyId}")
    public List<Map<String, Object>> getManualTenants(@PathVariable UUID propertyId) {
        return tenantService.getManualTenantsByProperty(propertyId);
    }

    // Add manual tenant
    @PostMapping("/manual")
    public Map<String, Object> addManualTenant(@RequestBody Map<String, String> req) {
        UUID propertyId = UUID.fromString(req.get("propertyId"));
        String name = req.get("name");
        String unit = req.get("unit");
        String phone = req.get("phone");
        return tenantService.addManualTenant(propertyId, name, unit, phone);
    }

    // Delete manual tenant
    @DeleteMapping("/manual/{id}")
    public void deleteManualTenant(@PathVariable UUID id) {
        tenantService.deleteManualTenant(id);
    }

    // Delete assigned tenant
    @DeleteMapping("/assignments/{id}")
    public void deleteAssignedTenant(@PathVariable UUID id) {
        tenantService.deleteAssignedTenant(id);
    }

    @GetMapping("/property/{propertyId}")
    public List<TenantViewDTO> getTenantsByProperty(@PathVariable UUID propertyId) {
        return tenantServices.getTenantsForProperty(propertyId);
    }

}