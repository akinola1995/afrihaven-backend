package com.caremyhome.repository;

import com.caremyhome.model.ManualTenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ManualTenantRepository extends JpaRepository<ManualTenant, UUID> {
    List<ManualTenant> findByPropertyId(UUID propertyId);
}