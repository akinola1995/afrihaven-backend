package com.caremyhome.controller;

// src/main/java/com/caremyhome/controller/DashboardController.java

import com.caremyhome.model.User;
import com.caremyhome.repository.*;
//import com.caremyhome.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryRepository inquiryRepository;

    @Autowired
    private MaintenanceRequestRepository maintenanceRequestRepository;

    @Autowired
    private RentRepository rentStatusRepository;

    @GetMapping("/{role}")
    public Map<String, Object> getDashboardData(@PathVariable String role) {
        Map<String, Object> data = new HashMap<>();
        role = role.toUpperCase();

        switch (role) {
            case "OWNER" -> {
                data.put("totalProperties", propertyRepository.count());
                data.put("totalTenants", userRepository.countByRole(User.Role.TENANT));
                data.put("pendingRents", rentStatusRepository.countByStatus("Unpaid"));
            }
            case "AGENT" -> {
                data.put("activeListings", propertyRepository.countByTypeIgnoreCase("Rent"));
                data.put("inquiries", inquiryRepository.count());
                data.put("totalTenants", userRepository.countByRole(User.Role.TENANT));
            }
            case "TENANT" -> {
                data.put("nextRentDue", rentStatusRepository.findNextDueForTenant());
                data.put("maintenanceRequests", maintenanceRequestRepository.countByStatusNot("Resolved"));
            }
            case "BUYER" -> {
                data.put("savedListings", propertyRepository.countSavedListings());
            }
            case "RENTER" -> {
                data.put("activeApplications", inquiryRepository.countActiveRentalApplications());
            }
            case "ADMIN" -> {
                data.put("totalUsers", userRepository.count());
                data.put("reportedListings", propertyRepository.countReported());
                data.put("totalProperties", propertyRepository.count());
            }
            default -> data.put("message", "Unknown role");
        }

        return data;
    }
}



//@RestController
//@RequestMapping("/api/dashboard")
//@CrossOrigin

//public class DashboardController {
//
//    @Autowired private PropertyRepository propertyRepo;
//    @Autowired private UserRepository userRepo;
//    @Autowired private MaintenanceRequestRepository maintenanceRepo;
//    @Autowired private InquiryRepository inquiryRepo;
//    // Add RentRepository, ApplicationRepository, etc. as needed
//
//    @GetMapping
//    public DashboardStatsDTO getStats(@RequestParam String email, @RequestParam String role) {
//        DashboardStatsDTO dto = new DashboardStatsDTO();
//
//        if (role.equals("Owner")) {
//            dto.totalProperties = propertyRepo.countByOwnerEmail(email);
//            dto.totalTenants = userRepo.countTenantsForOwner(email);
//            dto.pendingRents = 250000; // Replace with actual query
//        } else if (role.equals("Agent")) {
//            dto.totalTenants = userRepo.countTenantsAssignedToAgent(email);
//            dto.activeListings = propertyRepo.countByAgentEmail(email);
//            dto.inquiries = inquiryRepo.countByAgentEmail(email);
//        } else if (role.equals("Tenant")) {
//            dto.nextRentDue = "May 30, 2025"; // Replace with actual logic
//            dto.maintenanceRequests = maintenanceRepo.countByTenantEmail(email);
//        } else if (role.equals("Buyer")) {
//            dto.savedListings = 6;
//        } else if (role.equals("Renter")) {
//            dto.applications = 3;
//        } else if (role.equals("Admin")) {
//            dto.totalProperties = (int) propertyRepo.count();
//            dto.totalUsers = (int) userRepo.count();
//            dto.reportedListings = 1;
//        }
//
//        return dto;
//    }
//}
