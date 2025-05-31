// src/main/java/com/caremyhome/repository/UserRepository.java
package com.caremyhome.repository;

import com.caremyhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByRoleAndRegisteredBy(User.Role role, User registeredBy);
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
