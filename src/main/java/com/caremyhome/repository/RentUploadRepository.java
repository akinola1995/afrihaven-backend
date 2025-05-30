package com.caremyhome.repository;

public interface RentUploadRepository extends JpaRepository<RentUpload, UUID> {
    List<RentUpload> findByProperty(Property property);
    List<RentUpload> findByTenant(User tenant);
    // Add custom query if needed, e.g., for owner's properties
}