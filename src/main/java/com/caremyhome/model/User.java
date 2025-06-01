package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- AGENT ---
    @OneToMany(mappedBy = "assignedAgent")
    private List<Property> managedProperties;

    // --- TENANT ---
    @OneToMany(mappedBy = "tenant")
    private List<PropertyTenantAssignment> assignments;

    // --- AGENT Registration (track who added who) ---
    @ManyToOne
    @JoinColumn(name = "registered_by", columnDefinition = "uuid")
    private User registeredBy;

    public enum Role {
        ADMIN, OWNER, AGENT, TENANT, BUYER, RENTER, INQUIRER
    }

    @Column
    private String avatarUrl;
}
