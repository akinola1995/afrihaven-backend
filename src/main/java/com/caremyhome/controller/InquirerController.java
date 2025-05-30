package com.caremyhome.controller;

import com.caremyhome.dto.InquiryDTO;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.service.InquirerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inquirer")
public class InquirerController {
    @Autowired
    private InquirerService inquirerService;

    @GetMapping("/inquiries")
    public List<Map<String, Object>> getInquiries(@RequestParam String email) {
        return inquirerService.getInquiriesByEmail(email);
    }
}