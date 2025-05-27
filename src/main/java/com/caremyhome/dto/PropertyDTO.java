package com.caremyhome.dto;



@Data
public class PropertyDTO {
    private String title;
    private String description;
    private String type; // rent, sale, shortlet, vacation
    private String propertyType;
    private String state;
    private String city;
    private String country;
    private double price;
    private int bedrooms;
    private String imageUrl;
    private String ownerEmail; // passed from frontend
}
