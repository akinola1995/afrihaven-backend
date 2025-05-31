package com.caremyhome.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String type; // Rent, Inspection, Maintenance

    @Column(nullable = false)
    private String email; // who this reminder is for
}
