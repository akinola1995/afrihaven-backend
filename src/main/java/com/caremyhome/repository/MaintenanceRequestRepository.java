package com.caremyhome.repository;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
//    List<MaintenanceRequest> findByTenantEmail(String email);

    List<MaintenanceRequest> findByPropertyId(UUID propertyId);
    List<MaintenanceRequest> findByAgentEmail(String email);

    List<MaintenanceRequest> findByPropertyIn(List<Property> properties);

    List<MaintenanceRequest> findByPropertyId(String propertyId);
    long countByStatusNot(String status);
}