package com.caremyhome.controller;

import com.caremyhome.dto.PropertyDTO;
import com.caremyhome.model.Inquiry;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.InquiryRepository;
import com.caremyhome.repository.MessageRepository;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.UserRepository;
import com.caremyhome.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private PropertyRepository propertyRepo;

    // POST /api/properties
    @PostMapping
    public Property createProperty(@RequestBody PropertyDTO dto) {
        return propertyService.createProperty(dto);
    }

    @GetMapping
    public List<Map<String, Object>> getAllProperties(@RequestParam(required = false) String type) {
        List<Property> list;
        if (type != null && !type.isEmpty()) {
            list = propertyRepo.findByTypeIgnoreCase(type);
        } else {
            list = propertyRepo.findAll();
        }
        // Map results for frontend
        return list.stream().map(prop -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", prop.getId());
            map.put("title", prop.getTitle());
            map.put("description", prop.getDescription());
            map.put("price", prop.getPrice());
            map.put("bedrooms", prop.getBedrooms());
            map.put("city", prop.getCity());
            map.put("state", prop.getState());
            map.put("country", prop.getCountry());
            map.put("type", prop.getType());
            map.put("imageUrl", prop.getImageUrl());
            return map;
        }).toList();
    }
}
