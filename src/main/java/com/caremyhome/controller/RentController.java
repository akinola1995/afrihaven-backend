package com.caremyhome.controller;

import com.caremyhome.dto.RentDTO;
import com.caremyhome.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @GetMapping("/{propertyId}")
    public List<RentDTO> getRentsByProperty(@PathVariable UUID propertyId) {
        return rentService.getRentsForProperty(propertyId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRentStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        rentService.updateRentStatus(id, body.get("status"));
        return ResponseEntity.ok().build();
    }
}

