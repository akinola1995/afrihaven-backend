package com.caremyhome.controller;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired private AdminRepository adminRepo;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        admin.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(adminRepo.save(admin));
    }

    @GetMapping
    public List<Admin> getAdmins() {
        return adminRepo.findAll();
    }
}

