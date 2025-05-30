package com.caremyhome.repository;

import com.caremyhome.model.TenantAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.List;

public interface TenantAssignmentRepository extends JpaRepository<TenantAssignment, UUID> {


    List<TenantAssignment> findByAgentEmailAndActiveTrue(String agentEmail);
    List<TenantAssignment> findByAgentEmail(String agentEmail);
    List<TenantAssignment> findByOwnerEmail(String ownerEmail);
    List<TenantAssignment> findByPropertyId(String propertyId);

//    @Query("SELECT t FROM TenantAssignment t WHERE t.owner.id = :ownerId")
//    List<TenantAssignment> findByOwnerId(@Param("ownerId") UUID ownerId);
List<TenantAssignment> findByOwner_Id(UUID id);

    @Query("SELECT t FROM TenantAssignment t WHERE t.agentEmail = :email AND t.active = true")
    List<TenantAssignment> findActiveByAgentEmail(String email);

    @Query("SELECT t FROM TenantAssignment t WHERE t.agentEmail = :email AND t.active = false")
    List<TenantAssignment> findHistoryByAgentEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE TenantAssignment t SET t.active = false WHERE t.agentEmail = :agentEmail AND t.email = :tenantEmail")
    void deactivate(@org.springframework.data.repository.query.Param("agentEmail") String agentEmail,
                    @org.springframework.data.repository.query.Param("tenantEmail") String tenantEmail);
}
