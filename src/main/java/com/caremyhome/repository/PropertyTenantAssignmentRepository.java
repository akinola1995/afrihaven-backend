package com.caremyhome.repository;

import com.caremyhome.model.Property;
import com.caremyhome.model.PropertyTenantAssignment;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyTenantAssignmentRepository extends JpaRepository<PropertyTenantAssignment, UUID> {
    List<PropertyTenantAssignment> findByPropertyAssignedAgentAndStatus(User agent, String status);

    List<PropertyTenantAssignment> findByTenant(User tenant);

    List<PropertyTenantAssignment> findByAssignedBy(User agent);

    List<PropertyTenantAssignment> findByPropertyAssignedAgent(User agent);

    List<PropertyTenantAssignment> findByPropertyOwnerAndStatus(User owner, String status);
    boolean existsByTenantAndPropertyAndStatus(User tenant, Property property, String status);

    List<PropertyTenantAssignment> findByPropertyId(UUID propertyId);

    long count();

    List<PropertyTenantAssignment> findByProperty(Property property);

    // In PropertyTenantAssignmentRepository
    Optional<PropertyTenantAssignment> findFirstByTenantAndStatusOrderByAssignedAtDesc(User tenant, String status);
}