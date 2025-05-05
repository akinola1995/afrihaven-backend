package com.caremyhome.controller;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.service.MaintenanceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService service;

    public MaintenanceController(MaintenanceService service) {
        this.service = service;
    }

    @GetMapping
    public List<MaintenanceRequest> getAll() {
        return service.findAll();
    }

    @PostMapping
    public MaintenanceRequest create(@RequestBody MaintenanceRequest entity) {
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
