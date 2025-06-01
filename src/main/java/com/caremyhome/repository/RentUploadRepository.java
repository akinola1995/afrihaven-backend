package com.caremyhome.repository;

import com.caremyhome.model.RentUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentUploadRepository extends JpaRepository<RentUpload, UUID> {

    List<RentUpload> findByPropertyIdOrderByDueDateDesc(UUID propertyId);
    long countByStatus(String status);

    @Query("SELECT MIN(r.dueDate) FROM RentUpload r WHERE r.tenant = :tenant AND r.status = 'Unpaid'")
    Optional<Date> findNextRentDueByTenant(@Param("tenant") String tenant);
}