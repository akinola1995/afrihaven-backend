package com.caremyhome.repository;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
    List<MaintenanceRequest> findAllByOrderByDateDesc();
    List<MaintenanceRequest> findByTenantEmailOrderByCreatedAtDesc(String email);
    List<MaintenanceRequest> findByPropertyIdOrderByCreatedAtDesc(UUID propertyId);
    long countByTenantEmail(String email);
}