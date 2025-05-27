package com.caremyhome.dto;

public class RentDTO {
    private UUID id;
    private String tenant;
    private double amount;
    private LocalDate dueDate;
    private String status;

    public static RentDTO fromEntity(Rent rent) {
        RentDTO dto = new RentDTO();
        dto.setId(rent.getId());
        dto.setTenant(rent.getTenant());
        dto.setAmount(rent.getAmount());
        dto.setDueDate(rent.getDueDate());
        dto.setStatus(rent.getStatus());
        return dto;
    }

    // Getters and Setters
}

