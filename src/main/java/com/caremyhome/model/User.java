package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "avatar_url")
    private String avatarUrl;
    private String phone;
    private String role;
    private String fullName;
    private String email;
    private String password;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = String.valueOf(role);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Enumerated(EnumType.STRING)
//    private Role role;

    public enum Role {
        OWNER, AGENT, TENANT
    }

    // Getters and setters (or use Lombok later)
}