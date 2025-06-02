package com.caremyhome.controller;

import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import com.caremyhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET /api/users/{email}
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(Map.of(
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", user.getRole(),
                        "phone", user.getPhone(),
                        "avatarUrl", user.getAvatarUrl()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/users/{email}
    @PutMapping(value = "/{email}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateUser(
            @PathVariable String email,
            @RequestPart("name") String name,
            @RequestPart("phone") String phone,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    ) {
        try {
            User updated = userService.updateUser(email, name, phone, avatar);
            return ResponseEntity.ok(Map.of(
                    "name", updated.getName(),
                    "email", updated.getEmail(),
                    "role", updated.getRole(),
                    "phone", updated.getPhone(),
                    "avatarUrl", updated.getAvatarUrl()
            ));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to update avatar.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> form) {
        String email = form.get("email");
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Email already registered"));
        }

        String name = form.getOrDefault("fullName", form.getOrDefault("name", "User"));
        String phone = form.getOrDefault("phone", "");
        String password = form.get("password");

        // --- Require role ---
        String roleString = form.get("role");
        if (roleString == null || roleString.trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "No role selected. Please select a role."));
        }

        // --- Validate role ---
        User.Role role;
        try {
            role = User.Role.valueOf(roleString.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Invalid role selected. Please choose a valid role."));
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        User saved = userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "email", saved.getEmail(),
                "role", saved.getRole().name(),
                "name", saved.getName()
        ));
    }

    }



