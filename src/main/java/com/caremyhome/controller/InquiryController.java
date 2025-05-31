package com.caremyhome.controller;

import com.caremyhome.dto.InquiryDTO;
import com.caremyhome.model.Inquiry;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("/{propertyId}/inquiry")
    public ResponseEntity<?> submitInquiry(@PathVariable UUID propertyId, @RequestBody InquiryDTO req) {
        inquiryService.submitInquiry(propertyId, req);
        return ResponseEntity.ok().build();
    }
}