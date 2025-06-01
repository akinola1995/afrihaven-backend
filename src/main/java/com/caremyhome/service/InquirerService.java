package com.caremyhome.service;

import com.caremyhome.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InquirerService {
    @Autowired
    private InquiryRepository inquiryRepository;

    public List<Map<String, Object>> getInquiriesByEmail(String email) {
        return inquiryRepository.findByFromUserOrderByCreatedAtDesc(email)
                .stream()
                .map(inq -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("propertyName", inq.getProperty() != null ? inq.getProperty().getTitle() : "N/A");
                    map.put("status", inq.getStatus());
                    map.put("createdAt", inq.getCreatedAt()); // Use "createdAt" for consistency!
                    return map;
                })
                .collect(Collectors.toList());
    }
}
