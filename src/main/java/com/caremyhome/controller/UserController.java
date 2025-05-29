package com.caremyhome.controller;

import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"));
    }
    /*
     @GetMapping("/{email}")
     public ResponseEntity<User> getUser(@PathVariable String email) {
     return userRepository.findByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

     */

    @PutMapping(value = "/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @PathVariable String email,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        User user = userOpt.get();
        user.setFullName(name);
        user.setPhone(phone);

        if (avatar != null && !avatar.isEmpty()) {
            try {
                String filename = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
                Path uploadPath = Paths.get("uploads/avatars");
                Files.createDirectories(uploadPath);
                Files.copy(avatar.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                user.setAvatarUrl("/uploads/avatars/" + filename);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Avatar upload failed.");
            }
        }

        userRepository.save(user);
        return ResponseEntity.ok("User profile updated");
    }

        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody User user) {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already registered");
            }
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }
    }



