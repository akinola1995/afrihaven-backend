package com.caremyhome.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentUploadDTO {
    private String tenant;
    private String file;
    private double amount;
    private LocalDateTime date;
}