package com.caremyhome.controller;

import com.caremyhome.model.Admin;
import com.caremyhome.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepo;

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

