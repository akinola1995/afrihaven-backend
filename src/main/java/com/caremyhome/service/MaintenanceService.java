package com.caremyhome.service;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.repository.MaintenanceRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MaintenanceService {
    private final MaintenanceRequestRepository repo;

    public MaintenanceService(MaintenanceRequestRepository repo) {
        this.repo = repo;
    }

    public List<MaintenanceRequest> getByProperty(UUID propertyId) {
        return repo.findByPropertyIdOrderByCreatedAtDesc(propertyId);
    }

    @Transactional
    public MaintenanceRequest create(MaintenanceRequest request) {
        request.setCreatedAt(Instant.now());
        request.setStatus("Pending");
        return repo.save(request);
    }

    @Transactional
    public MaintenanceRequest updateStatus(UUID requestId, String status) {
        MaintenanceRequest req = repo.findById(requestId).orElseThrow();
        req.setStatus(status);
        return repo.save(req);
    }

    @Transactional
    public MaintenanceRequest addComment(UUID requestId, String from, String text) {
        MaintenanceRequest req = repo.findById(requestId).orElseThrow();
        MaintenanceRequest.Comment comment = new MaintenanceRequest.Comment();
        comment.setFromUser(from);
        comment.setText(text);
        comment.setDate(Instant.now());
        req.getComments().add(comment);
        return repo.save(req);
    }
}