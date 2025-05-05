package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String fullName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        OWNER, AGENT, TENANT
    }

    // Getters and setters (or use Lombok later)
}