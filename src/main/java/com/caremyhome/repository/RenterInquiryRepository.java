package com.caremyhome.repository;

import com.caremyhome.model.RenterInquiry;
import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RenterInquiryRepository extends JpaRepository<RenterInquiry, UUID> {
    List<RenterInquiry> findByRenter(User renter);
}
