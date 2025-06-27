// src/main/java/com/caremyhome/controller/AuthController.java
package com.caremyhome.controller;
import com.caremyhome.service.AuthService;
import com.caremyhome.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return authService.authenticate(email, password)
                .map(user -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", "success");
                    map.put("role", user.getRole());
                    map.put("email", user.getEmail());
                    map.put("name", user.getName());
                    return map;
                })
                .orElseGet(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("message", "failure");
                    return map;
                });
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        String role = payload.getOrDefault("role", "Tenant");
        String name = payload.getOrDefault("name", "User");

        if (authService.existsByEmail(email)) {
            return Map.of("message", "exists");
        }
        User user = authService.register(email, password, role, name);
        return Map.of(
                "message", "success",
                "role", user.getRole(),
                "email", user.getEmail(),
                "name", user.getName()
        );
    }
}