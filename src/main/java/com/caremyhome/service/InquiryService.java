package com.caremyhome.service;

import com.caremyhome.dto.InquiryDTO;
import com.caremyhome.model.Inquiry;
import com.caremyhome.model.Property;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepo;
    private final PropertyRepository propertyRepo;

    @Transactional
    public Inquiry submitInquiry(UUID propertyId, InquiryDTO req) {
        Property property = propertyRepo.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        Inquiry inq = new Inquiry();
        inq.setFrom(req.getEmail());
        inq.setName(req.getName());
        inq.setPhone(req.getPhone());
        inq.setMessage(req.getMessage());
        inq.setStatus("Open");
        inq.setProperty(property);
        if (req.getSubmittedAt() != null && !req.getSubmittedAt().isEmpty()) {
            inq.setCreatedAt(LocalDateTime.parse(req.getSubmittedAt().substring(0, 19))); // If you want to use submitted time
        } else {
            inq.setCreatedAt(LocalDateTime.now());
        }
        return inquiryRepo.save(inq);
    }
}