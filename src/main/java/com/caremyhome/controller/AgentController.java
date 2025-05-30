package com.caremyhome.controller;

import com.caremyhome.dto.AgentDashboardResponseDTO;
import com.caremyhome.dto.TenantAssignmentDto;
import com.caremyhome.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AgentController {
    @Autowired private AgentService agentService;

    // Dashboard data
    @GetMapping("/agent/{agentEmail}/dashboard")
    public AgentDashboardResponseDTO getDashboard(@PathVariable String agentEmail) {
        return agentService.getDashboard(agentEmail);
    }

    // Assign tenant to property
    @PostMapping("/assign-tenant")
    public void assignTenant(@RequestBody Map<String, String> data) {
        agentService.assignTenant(
                data.get("agentEmail"),
                data.get("email"), // tenant email
                data.get("unit"),
                UUID.fromString(data.get("propertyId"))
        );
    }

    // Unassign tenant
    @PostMapping("/unassign-tenant")
    public void unassignTenant(@RequestBody Map<String, String> data) {
        agentService.unassignTenant(
                data.get("agentEmail"),
                data.get("email") // tenant email
        );
    }

    // Register new agent
    @PostMapping("/admin/add-agent")
    public void addAgent(@RequestBody Map<String, String> data) {
        agentService.addAgent(
                data.get("name"),
                data.get("email"),
                data.get("registeredBy")
        );
    }
}
