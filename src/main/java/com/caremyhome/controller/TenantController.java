package com.caremyhome.controller;

import com.caremyhome.dto.TenantDTO;
import com.caremyhome.model.ManualTenant;
import com.caremyhome.model.TenantAssignment;
import com.caremyhome.repository.ManualTenantRepository;
import com.caremyhome.repository.TenantAssignmentRepository;
import com.caremyhome.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/{email}")
    public ResponseEntity<TenantDTO> getTenantDashboard(@PathVariable String email) {
        return ResponseEntity.ok(tenantService.getTenantDashboard(email));
    }

    @Autowired
    private TenantAssignmentRepository assignmentRepo;

    @Autowired
    private ManualTenantRepository manualRepo;

    @GetMapping("/assignments/{propertyId}")
    public List<TenantAssignment> getAssignments(@PathVariable String propertyId) {
        return assignmentRepo.findByPropertyId(propertyId);
    }

    @DeleteMapping("/assignments/{id}")
    public void deleteAssignment(@PathVariable UUID id) {
        assignmentRepo.deleteById(id);
    }

    @GetMapping("/manual/{propertyId}")
    public List<ManualTenant> getManualTenants(@PathVariable String propertyId) {
        return manualRepo.findByPropertyId(propertyId);
    }

    @PostMapping("/manual")
    public ManualTenant addManualTenant(@RequestBody ManualTenant tenant) {
        return manualRepo.save(tenant);
    }

    @DeleteMapping("/manual/{id}")
    public void deleteManualTenant(@PathVariable UUID id) {
        manualRepo.deleteById(id);
    }
}

