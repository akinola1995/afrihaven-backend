package com.caremyhome.controller;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/{email}")
    public ResponseEntity<TenantDTO> getTenantDashboard(@PathVariable String email) {
        return ResponseEntity.ok(tenantService.getTenantDashboard(email));
    }
}

