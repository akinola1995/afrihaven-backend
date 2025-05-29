package com.caremyhome.repository;

import com.caremyhome.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentRepository extends JpaRepository<Rent, UUID> {
    List<Rent> findByProperty_Id(UUID propertyId);
    long countByStatus(String status);

    String findNextDueForTenant();
}

