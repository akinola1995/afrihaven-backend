package com.caremyhome.service;

import com.caremyhome.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DashBoardService {

    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private PropertyTenantAssignmentRepository assignmentRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintenanceRepo;
    @Autowired private RentUploadRepository rentUploadRepo;

    public Map<String, Object> getDashboardStats(String role, String email) {
        Map<String, Object> stats = new HashMap<>();
        switch (role.toLowerCase()) {
            case "owner" -> {
                stats.put("totalProperties", propertyRepo.count()); // Or: propertyRepo.countByOwnerEmail(email)
                stats.put("totalTenants", assignmentRepo.count());
                stats.put("pendingRents", rentUploadRepo.countByStatus("Unpaid"));
            }
            case "agent" -> {
                stats.put("activeListings", propertyRepo.count()); // Or propertyRepo.countByAssignedAgentEmail(email)
                stats.put("inquiries", inquiryRepo.count());
                stats.put("totalTenants", assignmentRepo.count());
            }
            case "tenant" -> {
                stats.put("nextRentDue", rentUploadRepo.findNextRentDueByTenantEmail(email).orElse(LocalDate.parse("N/A")));
                stats.put("maintenanceRequests", maintenanceRepo.countByTenantEmail(email));
            }
            case "buyer" -> {
                stats.put("savedListings", 0); // Add logic if you track saved listings
            }
            case "renter" -> {
                stats.put("activeApplications", 0); // Add logic if you track rental applications
            }
            case "admin" -> {
                stats.put("totalUsers", userRepo.count());
                stats.put("reportedListings", 0); // Add logic for reports
                stats.put("totalProperties", propertyRepo.count());
            }
        }
        return stats;
    }
}
