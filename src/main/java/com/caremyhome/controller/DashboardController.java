package com.caremyhome.controller;

import com.caremyhome.service.DashBoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashBoardService dashboardService;

    @GetMapping("/{role}")
    public Map<String, Object> getDashboardStats(@PathVariable String role, @RequestParam(required = false) String email) {
        return dashboardService.getDashboardStats(role, email);
    }
}
