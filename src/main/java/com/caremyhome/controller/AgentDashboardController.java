package com.caremyhome.controller;

import com.caremyhome.dto.TenantAssignmentDto;
import com.caremyhome.service.AgentService;

@RestController
@RequestMapping("/api/agent")
public class AgentDashboardController {

    @Autowired private AgentService agentService;

    @GetMapping("/{email}/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable String email) {
        return ResponseEntity.ok(agentService.getAgentDashboard(email));
    }

    @PostMapping("/assign-tenant")
    public ResponseEntity<?> assignTenant(@RequestBody TenantAssignmentDto dto) {
        agentService.assignTenant(dto);
        return ResponseEntity.ok("Tenant assigned");
    }

    @PostMapping("/unassign-tenant")
    public ResponseEntity<?> unassignTenant(@RequestBody Map<String, String> request) {
        agentService.unassignTenant(request.get("agentEmail"), request.get("email"));
        return ResponseEntity.ok("Tenant unassigned");
    }
}
