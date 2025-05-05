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

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<MaintenanceRequest> maintenanceRequests = new ArrayList<>();

    // Getters and setters
}