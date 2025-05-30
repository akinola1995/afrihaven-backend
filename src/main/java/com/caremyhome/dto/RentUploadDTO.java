package com.caremyhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;✅ DTOs for RentUpload, Assignment, Unassignment
        java
        Copy
        Edit
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentUploadDTO {
    private String tenant;
    private String file;
    private double amount;
    private LocalDateTime date;
}