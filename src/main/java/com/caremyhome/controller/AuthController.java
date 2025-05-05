package com.caremyhome.controller;

import com.caremyhome.model.User;
import com.caremyhome.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        service.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

}