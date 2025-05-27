// src/main/java/com/caremyhome/controller/AuthController.java
package com.caremyhome.controller;

import com.caremyhome.dto.AuthRequest;
import com.caremyhome.dto.AuthResponse;
import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user != null) {
            return new AuthResponse(user.getEmail(), user.getRole().toString(), "success");
        } else {
            return new AuthResponse(null, null, "failure");
        }
    }
}
