package com.caremyhome.repository;

import com.caremyhome.model.TenantAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TenantAssignmentRepository extends JpaRepository<TenantAssignment, UUID> {
    List<TenantAssignment> findByPropertyId(String propertyId);
    List<TenantAssignment> findActiveByAgentEmail(String email);
    List<TenantAssignment> findHistoryByAgentEmail(String email);
    void deactivate(String agentEmail, String tenantEmail); // custom @Query
    List<TenantAssignment> findByOwnerId(UUID ownerId);
}
