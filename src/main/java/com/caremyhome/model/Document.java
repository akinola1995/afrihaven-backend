package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Document {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String url;
    private String description;

    @ManyToOne
    private User uploadedBy;

    @ManyToOne
    private Property property;

    // Getters and setters
}