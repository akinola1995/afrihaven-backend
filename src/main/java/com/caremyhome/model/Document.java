package com.caremyhome.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "property_documents")
@Data
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name; // original filename

    @Column
    private String description; // OPTIONAL: file description (nullable is OK)

    @Column(nullable = false)
    private String filePath; // this is your storage path on server/disk

    private String tenantEmail;

    @Column
    private String url; // actual downloadable or public URL, can be nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column(nullable = false)
    private Instant uploadedAt;
}
