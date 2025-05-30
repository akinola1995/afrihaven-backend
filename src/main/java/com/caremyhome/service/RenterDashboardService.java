package com.caremyhome.service;

import com.caremyhome.model.User;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.SavedPropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class RenterDashboardService {

    @Autowired private UserRepository userRepo;
    @Autowired private SavedPropertyRepository savedPropertyRepo;
    @Autowired private InquiryRepository inquiryRepo;

    // For /api/renter/saved
    public List<Map<String, Object>> getSavedProperties(String email) {
        User renter = userRepo.findByEmail(email);
        if (renter == null) return Collections.emptyList();

        return savedPropertyRepo.findByRenter(renter).stream().map(sp -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", sp.getProperty().getTitle());
            map.put("location", sp.getProperty().getLocation());
            map.put("status", sp.getStatus());
            return map;
        }).collect(Collectors.toList());
    }

    // For /api/renter/inquiries
    public List<Map<String, Object>> getRenterInquiries(String email) {
        return inquiryRepo.findByFromOrderByCreatedAtDesc(email).stream().map(inq -> {
            Map<String, Object> map = new HashMap<>();
            map.put("propertyName", inq.getProperty() != null ? inq.getProperty().getTitle() : "N/A");
            map.put("createdAt", inq.getCreatedAt());
            map.put("status", inq.getStatus());
            return map;
        }).collect(Collectors.toList());
    }
}
