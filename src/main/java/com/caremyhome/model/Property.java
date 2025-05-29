package com.caremyhome.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private String unit;
    private Double rent;
    private LocalDate nextDueDate;

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