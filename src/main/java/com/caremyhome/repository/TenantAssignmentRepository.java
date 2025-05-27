package com.caremyhome.repository;

import java.util.List;
import java.util.UUID;

public interface TenantAssignmentRepository extends JpaRepository<TenantAssignment, UUID> {
    List<TenantAssignment> findByPropertyId(String propertyId);
}
