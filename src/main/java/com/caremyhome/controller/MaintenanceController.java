package com.caremyhome.controller;

import com.caremyhome.model.MaintenanceComment;
import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.repository.MaintenanceRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceController(MaintenanceRequestRepository repo) {
        this.maintenanceRequestRepository = repo;
    }

    @GetMapping("/{propertyId}")
    public ResponseEntity<List<MaintenanceRequest>> getAll(@PathVariable Optional<UUID> propertyId) {
        List<MaintenanceRequest> result;
        if (propertyId.isPresent()) {
            result = maintenanceRequestRepository.findByProperty_Id(propertyId.get());
        } else {
            result = maintenanceRequestRepository.findAll();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<MaintenanceRequest> create(@RequestBody MaintenanceRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        MaintenanceRequest saved = maintenanceRequestRepository.save(request);
        return ResponseEntity.ok(saved);
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> payload) {
        maintenanceRequestRepository.findById(id).ifPresent(req -> req.setStatus(payload.get("status")));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    @Transactional
    public ResponseEntity<Void> addComment(@PathVariable UUID id, @RequestBody Map<String, String> comment) {
        maintenanceRequestRepository.findById(id).ifPresent(req -> {
            req.getComments().add(new MaintenanceComment(comment.get("from"), comment.get("text")));
        });
        return ResponseEntity.ok().build();
    }
}
