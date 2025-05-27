package com.caremyhome.repository;

import java.util.List;
import java.util.UUID;

public interface ManualTenantRepository extends JpaRepository<ManualTenant, UUID> {
    List<ManualTenant> findByPropertyId(String propertyId);
}