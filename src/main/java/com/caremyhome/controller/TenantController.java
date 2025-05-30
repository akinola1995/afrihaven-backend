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
import java.util.Map;
import java.util.UUID;

@RestController
public class TenantController {

    @Autowired
    private TenantService tenantDashboardService;

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
}

