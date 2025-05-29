package com.caremyhome.dto;

import com.caremyhome.model.Document;

import java.util.UUID;

public class DocumentDTO {
    private UUID id;
    private String name;
    private String url;
    private String description;
    private String filePath;

    public static DocumentDTO fromEntity(Document doc) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(doc.getId());
        dto.setName(doc.getName());
        dto.setUrl(doc.getUrl());
        dto.setDescription(doc.getDescription());
        dto.setFilePath(doc.getFilePath());
        return dto;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}
