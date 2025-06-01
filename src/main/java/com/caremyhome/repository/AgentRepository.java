package com.caremyhome.repository;

import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AgentRepository extends JpaRepository<User, UUID> {
//    List<User> findByRole(User.Role role);
//    List<User> findByRegisteredBy(String email); // used in your service
}
