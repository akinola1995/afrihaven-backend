// src/main/java/com/caremyhome/dto/TenantViewDTO.java
package com.caremyhome.dto;

import lombok.Data;

@Data
public class TenantViewDTO {
    private String name;
    private String email;
    private String unit;
    private String phone;
    private String rentStatus;

    public TenantViewDTO(String name, String email, String unit, String phone, String rentStatus) {
        this.name = name;
        this.email = email;
        this.unit = unit;
        this.phone = phone;
        this.rentStatus = rentStatus;
    }
}
