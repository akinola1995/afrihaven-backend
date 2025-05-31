package com.caremyhome.controller;

import com.caremyhome.dto.OwnerDashboardResponseDTO;
import com.caremyhome.service.OwnerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import lombok.*;

@RestController
public class OwnerDashboardController {
    @Autowired
    private OwnerDashboardService ownerDashboardService;

    @GetMapping("/api/dashboard/owner")
    public OwnerDashboardResponseDTO getOwnerDashboard(@RequestParam String email) {
        return ownerDashboardService.getOwnerDashboard(email);
    }

    @PostMapping("/api/tenant/assign")
    public void assignTenant(@RequestBody Map<String, String> data) {
        String tenantEmail = data.get("email");
        UUID propertyId = UUID.fromString(data.get("propertyId"));
        ownerDashboardService.assignTenant(tenantEmail, propertyId);
    }
}
