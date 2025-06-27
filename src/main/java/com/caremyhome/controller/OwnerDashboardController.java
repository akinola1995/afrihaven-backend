package com.caremyhome.controller;

import com.caremyhome.dto.OwnerDashboardResponseDTO;
import com.caremyhome.service.OwnerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class OwnerDashboardController {

    @Autowired
    private OwnerDashboardService ownerDashboardService;

    @GetMapping("/owner")
    public ResponseEntity<OwnerDashboardResponseDTO> getOwnerDashboard(@RequestParam String email) {
        return ResponseEntity.ok(ownerDashboardService.getOwnerDashboard(email));
    }

    @PostMapping("/tenant/assign")
    public ResponseEntity<?> assignTenant(@RequestBody Map<String, String> data) {
        try {
            String tenantEmail = data.get("email");
            UUID propertyId = UUID.fromString(data.get("propertyId"));
            ownerDashboardService.assignTenant(tenantEmail, propertyId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
