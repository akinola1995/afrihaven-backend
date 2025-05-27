package com.caremyhome.controller;

@RestController
@RequestMapping("/api/users")
public class UserStatsController {

    @Autowired private UserRepository userRepo;
    @Autowired private InquiryRepository inquiryRepo;

    @GetMapping("/stats")
    public Map<String, Integer> getUserCounts() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("tenants", userRepo.countByRole(User.Role.TENANT));
        stats.put("agents", userRepo.countByRole(User.Role.AGENT));
        stats.put("owners", userRepo.countByRole(User.Role.OWNER));
        stats.put("inquiries", (int) inquiryRepo.count());
        return stats;
    }
}

