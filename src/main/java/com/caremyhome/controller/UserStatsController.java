package com.caremyhome.controller;

import com.caremyhome.dto.UserStatsDTO;
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

    @Autowired
    private InquiryRepository inquiryRepo;

    @GetMapping("/stats")
    public UserStatsDTO getUserStats() {
        long tenants = userRepo.countByRole(User.Role.TENANT);
        long agents = userRepo.countByRole(User.Role.AGENT);
        long owners = userRepo.countByRole(User.Role.OWNER);
        long inquiries = inquiryRepo.count();

        return new UserStatsDTO(tenants, agents, owners, inquiries);
    }
}

