package com.caremyhome.service;

import com.caremyhome.model.Inquiry;
import com.caremyhome.repository.InquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry saveInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquiriesByPropertyId(UUID propertyId) {
        return inquiryRepository.findByPropertyId(propertyId);
    }

    public List<Inquiry> getAllInquiries() {
        return inquiryRepository.findAll();
    }
}
