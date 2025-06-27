package com.caremyhome.service;

import com.caremyhome.model.User;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {



    @Autowired
    private UserRepository userRepo;

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User updateUser(String email, String name, String phone, MultipartFile avatar) throws IOException {
        User user = userRepo.findByEmail(email).orElseThrow();
        user.setName(name);
        user.setPhone(phone);

        if (avatar != null && !avatar.isEmpty()) {
            String uploadDir = "uploads/avatars";
            String filename = email.replaceAll("@", "_") + "_" + avatar.getOriginalFilename();
            File dest = new File(uploadDir, filename);
            dest.getParentFile().mkdirs();
            avatar.transferTo(dest);
            user.setAvatarUrl("/uploads/avatars/" + filename); // set the path for frontend
        }

        return userRepo.save(user);
    }


    public User registerUser(Map<String, String> form) {
        String email = form.get("email");
        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(form.get("fullName"));
        user.setEmail(email);
        user.setPhone(form.get("phone"));
        user.setPassword(form.get("password")); // Hash this in real life!
        user.setRole(User.Role.valueOf(form.get("role")));
        return userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User save(User entity) {
        return userRepo.save(entity);
    }

}