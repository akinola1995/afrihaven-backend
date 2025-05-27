package com.caremyhome.repository;
public interface RentRepository extends JpaRepository<Rent, UUID> {
    List<Rent> findByProperty_Id(UUID propertyId);
    long countByStatus(String status);
    String findNextDueForTenant();
}

