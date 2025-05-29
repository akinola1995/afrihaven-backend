package com.caremyhome.controller;

import com.caremyhome.model.Inquiry;
import com.caremyhome.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {

    @Autowired
    private InquiryRepository inquiryRepo;

    @GetMapping("/by-email")
    public List<Inquiry> getByEmail(@RequestParam String email) {
        return inquiryRepo.findByEmail(email);
    }

    @PostMapping
    public Inquiry submitInquiry(@RequestBody Inquiry inquiry) {
        return inquiryRepo.save(inquiry);
    }

    @GetMapping
    public List<Inquiry> getAll() {
        return inquiryRepo.findAll();
    }
}


