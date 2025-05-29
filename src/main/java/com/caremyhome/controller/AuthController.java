// src/main/java/com/caremyhome/controller/AuthController.java
package com.caremyhome.controller;
import com.caremyhome.service.EmailService;
import org.springframework.mail.SimpleMailMessage;

import com.caremyhome.dto.AuthRequest;
import com.caremyhome.dto.AuthResponse;
import com.caremyhome.dto.RegisterDTO;
import com.caremyhome.dto.UserDTO;
import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        if (user != null) {
            return new AuthResponse(user.getEmail(), user.getRole().toString(), "success");
        } else {
            return new AuthResponse(null, null, "failure");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Always hash
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());

        userRepository.save(user);

        // Simulate sending email
        emailService.sendWelcomeEmail(dto.getEmail(), dto.getFullName());

        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }


//    public void sendWelcomeEmail(String to, String name) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject("Welcome to AfriHaven");
//        message.setText("Dear " + name + ",\n\nWelcome to AfriHaven! We're excited to have you.\n\nCheers,\nAfriHaven Team");
//        mailSender.send(message);
//    }

}
