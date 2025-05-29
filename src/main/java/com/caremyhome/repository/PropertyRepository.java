package com.caremyhome.repository;

import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID> {

    List<Property> findByAgentEmail(String email);
    List<Property> findByTypeIgnoreCase(String type);
    long countByTypeIgnoreCase(String type);
    long countReported();
    long countSavedListings();
    List<Property> findByOwner(User owner);
}