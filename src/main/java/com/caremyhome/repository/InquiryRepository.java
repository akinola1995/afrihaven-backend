package com.caremyhome.repository;

import com.caremyhome.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {
    List<Inquiry> findByEmail(String email);
    List<Inquiry> findByProperty_Id(UUID propertyId);
    long countActiveRentalApplications()
}
