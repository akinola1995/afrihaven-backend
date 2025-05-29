package com.caremyhome.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
public class ManualTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String unit;

    private String phone;

    private String propertyId;

    // Getters & Setters

}
