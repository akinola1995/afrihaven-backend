package com.caremyhome.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RentStatusDto {
    private UUID id;
    private String tenant;
    private double amount;
    private LocalDate dueDate;
    private String status;
}
