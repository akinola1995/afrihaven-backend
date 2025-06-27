package com.caremyhome.repository;

import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID>, JpaSpecificationExecutor<Property> {
    List<Property> findByAssignedAgent(User agent);
    List<Property> findByOwner(User owner);

    List<Property> findByTypeIgnoreCase(String type);
    long countByOwnerEmail(String email);
    long countByAssignedAgentEmail(String email);

    // REMOVE: Property findByTenantEmail(String email);

    // If you want to get properties for a tenant, use the assignment repository:
    // List<PropertyTenantAssignment> findByEmail(String email); (in PropertyTenantAssignmentRepository)
}
