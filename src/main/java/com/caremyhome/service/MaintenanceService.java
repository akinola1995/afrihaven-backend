package com.caremyhome.service;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.repository.MaintenanceRequestRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class MaintenanceService {

    private final MaintenanceRequestRepository repository;

    public MaintenanceService(MaintenanceRequestRepository repository) {
        this.repository = repository;
    }

    public List<MaintenanceRequest> findAll() {
        return repository.findAll();
    }

    public MaintenanceRequest save(MaintenanceRequest entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}