package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private String type; // RENT, SALE, SHORTLET, VACATION
    private String state;
    private String city;
    private String propertyType;
    private int price;
    private int bedrooms;
    private String country;
    private String imageUrl;

    private String videoUrl;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();

    // Getters and setters
}