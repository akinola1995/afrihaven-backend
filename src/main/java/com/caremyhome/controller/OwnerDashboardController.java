package com.caremyhome.controller;

import com.caremyhome.dto.OwnerDashboardDTO;
import com.caremyhome.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard/owner")
@CrossOrigin
public class OwnerDashboardController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public OwnerDashboardDTO getDashboard(@RequestParam String email) {
        return ownerService.getOwnerDashboard(email);
    }
}
