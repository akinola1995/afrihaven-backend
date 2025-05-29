package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tenant {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String fullName;
    private String email;
    private String phone;

    @ManyToOne
    private Property assignedProperty;

    // Getters and setters
}