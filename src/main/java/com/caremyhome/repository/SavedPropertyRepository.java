package com.caremyhome.repository;

import com.caremyhome.model.SavedProperty;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SavedPropertyRepository extends JpaRepository<SavedProperty, UUID> {
    List<SavedProperty> findByRenter(User renter);
}
