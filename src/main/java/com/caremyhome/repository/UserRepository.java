// src/main/java/com/caremyhome/repository/UserRepository.java
package com.caremyhome.repository;

import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmailAndPassword(String email, String password);
    long countByRole(Role role);
}
