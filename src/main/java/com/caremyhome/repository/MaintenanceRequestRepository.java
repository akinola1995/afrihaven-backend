package com.caremyhome.repository;

import com.afrihaven.model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
//    List<MaintenanceRequest> findByTenantEmail(String email);
    List<MaintenanceRequest> findByPropertyId(String propertyId);
    long countByStatusNot(String status);
}