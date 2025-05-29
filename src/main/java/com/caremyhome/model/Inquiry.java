package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private User agent;

    private String message;

    private String email;

    private String status;

    private LocalDateTime createdAt;



    @ManyToOne
    private Property property;

    @PrePersist
    public void setTimestamp() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    private String name;
    private String phone;

//    private LocalDateTime submittedAt;
//
//
//    @PrePersist
//    public void onCreate() {
//        this.submittedAt = LocalDateTime.now();
//    }

}
