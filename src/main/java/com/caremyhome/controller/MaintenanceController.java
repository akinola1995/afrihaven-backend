package com.caremyhome.controller;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceController(MaintenanceRequestRepository repo) {
        this.maintenanceRequestRepository = repo;
    }

    @GetMapping("/{propertyId}")
    public List<MaintenanceRequest> getAll(@PathVariable Optional<String> propertyId) {
        if (propertyId.isPresent() && !propertyId.get().isEmpty()) {
            return maintenanceRequestRepository.findByPropertyId(propertyId.get());
        }
        return maintenanceRequestRepository.findAll();
    }

    @PostMapping
    public MaintenanceRequest create(@RequestBody MaintenanceRequest request) {
        request.setCreatedAt(new Date());
        request.setStatus("Pending");
        return maintenanceRequestRepository.save(request);
    }

    @PatchMapping("/{id}/status")
    @Transactional
    public void updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> payload) {
        maintenanceRequestRepository.findById(id).ifPresent(req -> req.setStatus(payload.get("status")));
    }

    @PostMapping("/{id}/comment")
    @Transactional
    public void addComment(@PathVariable UUID id, @RequestBody Map<String, String> comment) {
        maintenanceRequestRepository.findById(id).ifPresent(req -> {
            req.getComments().add(new MaintenanceComment(comment.get("from"), comment.get("text")));
        });
    }
}