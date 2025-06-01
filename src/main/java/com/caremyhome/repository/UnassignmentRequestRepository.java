package com.caremyhome.repository;

import com.caremyhome.model.UnassignmentRequest;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface UnassignmentRequestRepository extends JpaRepository<UnassignmentRequest, UUID> {

    List<UnassignmentRequest> findByOwnerEmail(String ownerEmail);
    List<UnassignmentRequest> findByOwner(User owner);
}
