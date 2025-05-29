package com.caremyhome.repository;

import com.caremyhome.model.UnassignmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UnassignmentRequestRepository extends JpaRepository<UnassignmentRequest, UUID> {
    List<UnassignmentRequest> findByOwner(UUID ownerId);
}
