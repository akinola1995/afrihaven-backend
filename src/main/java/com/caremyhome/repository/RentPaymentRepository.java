package com.caremyhome.repository;

import com.caremyhome.model.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentPaymentRepository extends JpaRepository<RentPayment, UUID> {
}