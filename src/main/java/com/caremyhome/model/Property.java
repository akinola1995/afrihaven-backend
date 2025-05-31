package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    private String propertyType;

    // --- THIS IS ALL YOU NEED FOR MULTIPLE IMAGES ---
    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    // If you want, you can have a 'mainImage' field for convenience (optional):
    // private String mainImage;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int bedrooms;

    @Column(nullable = false)
    private String status; // e.g., "Available", "Rented", "Sold"

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private double rent;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private User assignedAgent;

    private String ownerEmail;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<PropertyTenantAssignment> assignments;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<MaintenanceRequest> maintenanceRequests;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Inquiry> inquiries;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<RentUpload> rentUploads;

    @Column(nullable = false)
    private String location;

    // Store video URL/path (optional)
    private String videoUrl;

    public LocalDate nextDueDate;
}

