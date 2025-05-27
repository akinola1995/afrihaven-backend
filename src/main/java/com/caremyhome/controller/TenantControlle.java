package com.caremyhome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

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
