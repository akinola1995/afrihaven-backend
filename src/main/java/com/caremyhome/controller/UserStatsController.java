package com.caremyhome.controller;

import com.caremyhome.model.User;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserStatsController {

    @Autowired
    private UserRepository userRepo;
    @Autowired private InquiryRepository inquiryRepo;

    @GetMapping("/stats")
    public Map<String, Integer> getUserCounts() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("tenants", (int) userRepo.countByRole(User.Role.TENANT));
        stats.put("agents", (int) userRepo.countByRole(User.Role.AGENT));
        stats.put("owners", (int) userRepo.countByRole(User.Role.OWNER));
        stats.put("inquiries", (int) inquiryRepo.count());
        return stats;
    }
}

