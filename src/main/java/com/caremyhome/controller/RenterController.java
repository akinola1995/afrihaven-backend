package com.caremyhome.controller;

import com.caremyhome.model.RenterInquiry;
import com.caremyhome.model.SavedProperty;
import com.caremyhome.model.User;
import com.caremyhome.repository.RenterInquiryRepository;
import com.caremyhome.repository.SavedPropertyRepository;
import com.caremyhome.repository.UserRepository;
import com.caremyhome.service.RenterDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/renter")
public class RenterController {

    @Autowired
    private RenterDashboardService renterDashboardService;

    // GET /api/renter/saved?email=...
    @GetMapping("/saved")
    public List<Map<String, Object>> getSaved(@RequestParam String email) {
        return renterDashboardService.getSavedProperties(email);
    }

    // GET /api/renter/inquiries?email=...
    @GetMapping("/inquiries")
    public List<Map<String, Object>> getInquiries(@RequestParam String email) {
        return renterDashboardService.getRenterInquiries(email);
    }
}

