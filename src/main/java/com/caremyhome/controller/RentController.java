package com.caremyhome.controller;

import com.caremyhome.dto.RentStatusDto;
import com.caremyhome.service.RentUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/rents")
public class RentController {
    private final RentUploadService rentUploadService;

    public RentController(RentUploadService rentUploadService) {
        this.rentUploadService = rentUploadService;
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<List<RentStatusDto>> getRents(@PathVariable UUID propertyId) {
        return ResponseEntity.ok(rentUploadService.getRentStatusByProperty(propertyId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRentStatus(@PathVariable UUID id, @RequestBody Map<String, String> req) {
        String status = req.get("status");
        rentUploadService.updateRentStatus(id, status);
        return ResponseEntity.ok().build();
    }
}