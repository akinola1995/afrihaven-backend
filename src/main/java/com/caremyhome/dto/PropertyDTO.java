package com.caremyhome.dto;


import lombok.Data;

import java.util.List;

@Data
public class PropertyDTO {
    private String title;
    private String description;
    private String type; // rent, sale, shortlet, vacation
    private String propertyType;
    private String state;
    private String city;
    private String country;
    private int price;
    private int bedrooms;
    private String imageUrl;
    private List<String> images;
    private String ownerEmail; // passed from frontend
}
