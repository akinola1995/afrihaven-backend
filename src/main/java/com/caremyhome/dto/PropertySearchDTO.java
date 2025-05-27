// src/main/java/com/caremyhome/dto/PropertySearchDTO.java
package com.caremyhome.dto;

import lombok.Data;

@Data
public class PropertySearchDTO {
    private String type;
    private String propertyType;
    private String minPrice;
    private String maxPrice;
    private String bedrooms;
    private String country;
    private String state;
}
