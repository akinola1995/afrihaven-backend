package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Tenant {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String phone;

    @ManyToOne
    private Property assignedProperty;

    // Getters and setters
}