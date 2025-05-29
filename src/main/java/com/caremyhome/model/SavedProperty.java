package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SavedProperty {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String location;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User renter;

    public enum Status {
        Pending, Approved, Rejected
    }

    // Getters and Setters
}
