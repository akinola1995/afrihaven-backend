package com.caremyhome.service;

import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User register(String email, String password, String role, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // NOTE: Hash in production!
        user.setRole(User.Role.valueOf(role));
        user.setName(name);
        return userRepository.save(user);
    }
}
