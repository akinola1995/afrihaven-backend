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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }

        // Hash the password!
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not provided
        if (user.getRole() == null) {
            user.setRole(User.Role.INQUIRER); // Or whatever your default is
        }

        User saved = userRepository.save(user);

        // Send minimal safe response for frontend
        return ResponseEntity.ok(Map.of(
                "email", saved.getEmail(),
                "role", saved.getRole(),
                "name", saved.getName()
        ));
    }
    }



