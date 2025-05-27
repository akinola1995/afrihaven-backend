package com.caremyhome.model;

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tenant;

    private double amount;

    private LocalDate dueDate;

    private String status;

    @ManyToOne
    private Property property;

    // Getters and Setters
}

