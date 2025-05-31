package com.caremyhome.service;

import com.caremyhome.dto.UserStatsDTO;
import com.caremyhome.model.Inquiry;
import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.MaintenanceRequestRepository;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AdminService {
    @Autowired private UserRepository userRepo;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private InquiryRepository inquiryRepo;
    @Autowired private MaintenanceRequestRepository maintRepo;

    public User addAdmin(String name, String email) {
        if (userRepo.existsByEmail(email)) throw new RuntimeException("Email already exists");
        User admin = new User();
        admin.setName(name);
        admin.setEmail(email);
        admin.setRole(User.Role.ADMIN);
        admin.setCreatedAt(java.time.LocalDateTime.now());
        return userRepo.save(admin);
    }

    public List<User> getAdmins() { return userRepo.findByRole(User.Role.ADMIN); }
    public List<Property> getAllProperties() { return propertyRepo.findAll(); }
    public List<Inquiry> getAllInquiries() { return inquiryRepo.findAllByOrderByCreatedAtDesc(); }
    public List<MaintenanceRequest> getAllMaintenanceRequests() { return maintRepo.findAllByOrderByCreatedAtDesc(); }
    public UserStatsDTO getUserStats() {
        long tenants = userRepo.findByRole(User.Role.TENANT).size();
        long agents = userRepo.findByRole(User.Role.AGENT).size();
        long owners = userRepo.findByRole(User.Role.OWNER).size();
        long inquiries = inquiryRepo.count();
        return new UserStatsDTO(tenants, agents, owners, inquiries);
    }
}
