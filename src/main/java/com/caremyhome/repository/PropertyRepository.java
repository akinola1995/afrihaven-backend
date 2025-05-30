package com.caremyhome.repository;

import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID>, JpaSpecificationExecutor<Property> {
    List<Property> findByAssignedAgent(User agent);
    List<Property> findByOwner(User owner);
    Property findByTenantEmail(String email);
    List<Property> findByTypeIgnoreCase(String type);
}