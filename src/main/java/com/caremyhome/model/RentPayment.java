package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RentPayment {
    @Id
    @GeneratedValue
    private UUID id;

    private Double amount;
    private LocalDate date;
    private String status;
    private String receiptUrl;

    @ManyToOne
    private Property property;

    @ManyToOne
    private Tenant tenant;

    // Getters and setters
}