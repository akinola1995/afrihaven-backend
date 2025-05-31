package com.caremyhome.controller;

import com.caremyhome.dto.UserStatsDTO;
import com.caremyhome.model.Admin;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.AdminRepository;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.MaintenanceRequestRepository;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AdminController {
    @Autowired private AdminService adminService;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintRepo;

    @GetMapping("/admins")
    public List<User> getAdmins() {
        return adminService.getAdmins();
    }

    @PostMapping("/admins")
    public User addAdmin(@RequestBody Map<String, String> body) {
        return adminService.addAdmin(body.get("name"), body.get("email"));
    }

    @GetMapping("/properties")
    public List<Map<String, Object>> getProperties() {
        List<Property> props = propertyRepo.findAll();
        return props.stream().map(p -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", p.getId());
            m.put("title", p.getTitle());
            m.put("ownerEmail", p.getOwner() != null ? p.getOwner().getEmail() : null);
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/users/stats")
    public UserStatsDTO getUserStats() {
        return adminService.getUserStats();
    }

    @GetMapping("/inquiries")
    public List<Map<String, Object>> getInquiries() {
        return inquiryRepo.findAllByOrderByCreatedAtDesc().stream().map(inq -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", inq.getId());
            m.put("from", inq.getFrom());
            m.put("message", inq.getMessage());
            m.put("propertyId", inq.getProperty() != null ? inq.getProperty().getId() : null);
            m.put("date", inq.getDate());
            return m;
        }).collect(Collectors.toList());
    }

    @GetMapping("/maintenance")
    public List<Map<String, Object>> getMaintenance() {
        return maintRepo.findAllByOrderByCreatedAtDesc().stream().map(mr -> {
            Map<String, Object> m = new HashMap<>();
            m.put("id", mr.getId());
            m.put("issue", mr.getIssue());
            m.put("status", mr.getStatus());
            m.put("propertyId", mr.getPropertyId());
            m.put("date", mr.getCreatedAt());
            return m;
        }).collect(Collectors.toList());
    }
}