package com.caremyhome.controller;

import com.caremyhome.model.Inquiry;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inquiries")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    // GET /api/inquiries/by-email?email=someone@email.com
    @GetMapping("/by-email")
    public List<Inquiry> getInquiriesByEmail(@RequestParam String email) {
        return inquiryService.getInquiriesByEmail(email);
    }
}