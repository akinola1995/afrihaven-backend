package com.caremyhome.model;

import jakarta.persistence.*;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "saved_properties")
@Data
@NoArgsConstructor
public class SavedProperty {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "renter_id", columnDefinition = "uuid")
    private User renter;  // The user who saved the property

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    private String status; // e.g. "Approved", "Pending"
}