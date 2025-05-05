package com.caremyhome.repository;

import com.caremyhome.model.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, UUID> {
}