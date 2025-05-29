package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RenterInquiry {

    @Id
    @GeneratedValue
    private UUID id;

    private String property;
    private String status;

    private LocalDateTime createdAt;

    @ManyToOne
    private User renter;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
}
