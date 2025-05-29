package com.caremyhome.dto;

import com.caremyhome.model.MaintenanceRequest;
import com.caremyhome.model.Property;
import lombok.Data;

@Data
public class MaintenanceDTO {
    private String issue;
    private String status;
    private String date;
    private String propertyTitle;

    public MaintenanceDTO(String issue, String status, String date, Property propertyTitle) {
        this.issue = issue;
        this.status = status;
        this.date = date;
        this.propertyTitle = String.valueOf(propertyTitle);
    }

    public static MaintenanceDTO fromEntity(MaintenanceRequest request) {
        return new MaintenanceDTO(
                request.getIssue(),
                request.getStatus(),
                request.getCreatedAt().toString(), // Or format with DateTimeFormatter
                request.getProperty()
        );
    }

    // Getters and Setters
}
