package com.caremyhome.repository;

import com.caremyhome.model.Inquiry;
import com.caremyhome.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {
    List<Inquiry> findByEmail(String email);
    List<Inquiry> findByPropertyId(UUID propertyId);
    List<Inquiry> findByAgentEmail(String email);
    List<Inquiry> findByPropertyIn(List<Property> properties);
    long countActiveRentalApplications();
}
