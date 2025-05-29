package com.caremyhome.controller;

import com.caremyhome.dto.InquiryDTO;
import com.caremyhome.repository.InquiryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inquirer")
public class InquirerController {

    private final InquiryRepository inquiryRepository;

    public InquirerController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/inquiries")
    public List<InquiryDTO> getInquiriesByEmail(@RequestParam String email) {
        return inquiryRepository.findByEmail(email).stream()
                .map(inq -> new InquiryDTO(
                        inq.getProperty().getTitle(),
                        inq.getStatus(),
                        inq.getSubmittedAt().toString()
                ))
                .collect(Collectors.toList());
    }
}
