package com.caremyhome.service;

import com.caremyhome.model.Inquiry;
import com.caremyhome.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InquiryService {
    @Autowired
    private InquiryRepository inquiryRepository;

    public List<Inquiry> getInquiriesByEmail(String email) {
        return inquiryRepository.findByFromOrderByCreatedAtDesc(email);
    }
}