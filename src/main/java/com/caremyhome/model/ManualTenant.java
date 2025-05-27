package com.caremyhome.model;

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
