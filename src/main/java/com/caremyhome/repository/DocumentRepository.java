package com.caremyhome.repository;

import com.caremyhome.model.Document;
import com.caremyhome.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {
    List<Document> findByProperty(Property property);

    List<Document> findByPropertyId(UUID propertyId);
    List<Document> findByTenantEmailOrderByUploadedAtDesc(String email);
    List<Document> findByProperty_Id(UUID propertyId);
}
