package com.caremyhome.model;

@Entity
public class Admin {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private LocalDateTime createdAt;

    // getters/setters
}

