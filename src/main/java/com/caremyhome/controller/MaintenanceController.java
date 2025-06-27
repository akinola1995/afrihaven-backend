package com.caremyhome.controller;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.model.Property;
import com.caremyhome.repository.MaintenanceRequestRepository;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.service.MaintenanceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {
    @Autowired
    private PropertyRepository propertyRepository;
    private final MaintenanceService service;

    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    // GET /api/maintenance/{propertyId}
    @GetMapping("/{propertyId}")
    public List<Map<String, Object>> getRequests(@PathVariable UUID propertyId) {
        return service.getByProperty(propertyId)
                .stream()
                .map(req -> Map.of(
                        "id", req.getId(),
                        "tenantEmail", req.getTenantEmail(),
                        "tenantName", req.getTenantName(),
                        "propertyId", req.getProperty() != null ? req.getProperty().getId() : null, // <- HERE
                        "issue", req.getIssue(),
                        "urgency", req.getUrgency(),
                        "status", req.getStatus(),
                        "createdAt", req.getCreatedAt(),
                        "comments", req.getComments() == null ? new ArrayList<>() :
                                req.getComments().stream().map(c -> Map.of(
                                        "from", c.getFromUser(),
                                        "text", c.getText(),
                                        "date", c.getDate()
                                )).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
    // POST /api/maintenance
    @PostMapping
    public ResponseEntity<?> createRequest(@RequestBody Map<String, Object> payload) {
        try {
            MaintenanceRequest req = new MaintenanceRequest();
            // Get Property by id, set it as relationship!
            Property property = propertyRepository.findById(UUID.fromString((String) payload.get("propertyId")))
                    .orElseThrow(() -> new RuntimeException("Property not found"));
            req.setProperty(property);
            req.setTenantEmail((String) payload.get("tenantEmail"));
            req.setTenantName((String) payload.get("tenantName"));
            req.setIssue((String) payload.get("issue"));
            req.setUrgency((String) payload.getOrDefault("urgency", "Medium"));
            req.setCreatedAt(java.time.Instant.now());
            req.setStatus("Pending");
            MaintenanceRequest saved = service.create(req);
            return ResponseEntity.ok(Map.of("id", saved.getId()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // PATCH /api/maintenance/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            MaintenanceRequest updated = service.updateStatus(id, status);
            return ResponseEntity.ok(Map.of("status", updated.getStatus()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // POST /api/maintenance/{id}/comment
    @PostMapping("/{id}/comment")
    public ResponseEntity<?> addComment(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        try {
            String from = body.get("from");
            String text = body.get("text");
            MaintenanceRequest req = service.addComment(id, from, text);
            return ResponseEntity.ok(Map.of(
                    "comments", req.getComments().stream().map(c -> Map.of(
                            "from", c.getFromUser(),
                            "text", c.getText(),
                            "date", c.getDate()
                    )).collect(Collectors.toList())
            ));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}