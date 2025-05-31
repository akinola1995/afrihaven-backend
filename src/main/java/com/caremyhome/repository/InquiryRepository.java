package com.caremyhome.repository;

import com.caremyhome.model.Inquiry;
import com.caremyhome.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InquiryRepository extends JpaRepository<Inquiry, UUID> {
    List<Inquiry> findByFromOrderByCreatedAtDesc(String from);
    List<Inquiry> findByPropertyIdOrderByCreatedAtDesc(UUID propertyId);
    long count();
}
