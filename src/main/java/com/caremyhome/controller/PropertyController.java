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
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/properties")
public class PropertyController {
    @Autowired private PropertyService propertyService;
    @Autowired private PropertyRepository propertyRepo;
    @Autowired private UserRepository userRepo;

    // For simple JSON requests (can keep for API/testing)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Property createProperty(@RequestBody PropertyDTO dto) {
        return propertyService.createProperty(dto);
    }

    // For multipart/form-data (matches your new AddProperty UI)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProperty(
            @RequestPart("property") PropertyDTO propertyDTO,
            @RequestPart("images") List<MultipartFile> images,
            @RequestPart(value = "video", required = false) MultipartFile video
    ) {
        try {
            Property property = propertyService.createPropertyWithMedia(propertyDTO, images, video);
            return ResponseEntity.ok(property);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Your GET endpoint as before, no change needed.
    @GetMapping
    public List<Map<String, Object>> getAllProperties(@RequestParam(required = false) String type) {
        List<Property> list = (type != null && !type.isEmpty())
                ? propertyRepo.findByTypeIgnoreCase(type)
                : propertyRepo.findAll();

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
            map.put("images", prop.getImages());
            return map;
        }).toList();
    }

    @GetMapping("/search")
    public List<Map<String, Object>> searchProperties(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String bedrooms,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String state
    ) {
        List<Property> results = propertyRepo.findAll();
        // Filter in memory, or use JPA Criteria for production scale
        return results.stream()
                .filter(p -> type == null || type.isEmpty() || p.getType().equalsIgnoreCase(type))
                .filter(p -> propertyType == null || propertyType.isEmpty() ||
                        (p.getPropertyType() != null && p.getPropertyType().equalsIgnoreCase(propertyType)))
                .filter(p -> minPrice == null || minPrice.isEmpty() || p.getPrice() >= Double.parseDouble(minPrice))
                .filter(p -> maxPrice == null || maxPrice.isEmpty() || p.getPrice() <= Double.parseDouble(maxPrice))
                .filter(p -> bedrooms == null || bedrooms.isEmpty() || p.getBedrooms() == Integer.parseInt(bedrooms))
                .filter(p -> country == null || country.isEmpty() || p.getCountry().equalsIgnoreCase(country))
                .filter(p -> state == null || state.isEmpty() || p.getState().equalsIgnoreCase(state))
                .map(p -> Map.of(
                        "id", p.getId(),
                        "title", p.getTitle(),
                        "description", p.getDescription(),
                        "price", p.getPrice(),
                        "bedrooms", p.getBedrooms(),
                        "country", p.getCountry(),
                        "state", p.getState(),
                        "type", p.getType(),
                        "images", p.getImages()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{email}")
    public ResponseEntity<List<Map<String, Object>>> getPropertiesByOwner(@PathVariable String email) {
        Optional<User> ownerOpt = userRepo.findByEmail(email);
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Property> properties = propertyRepo.findByOwner(ownerOpt.get());

        // Map to frontend-friendly output
        List<Map<String, Object>> result = properties.stream().map(prop -> {
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
            map.put("images", prop.getImages());
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }

    // Get all properties managed by an agent (by email)
    @GetMapping("/agent/{email}")
    public ResponseEntity<List<Map<String, Object>>> getPropertiesByAgent(@PathVariable String email) {
        Optional<User> agentOpt = userRepo.findByEmail(email);
        if (agentOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Property> properties = propertyRepo.findByAssignedAgent(agentOpt.get());

        // Map to frontend-friendly output
        List<Map<String, Object>> result = properties.stream().map(prop -> {
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
            map.put("images", prop.getImages());
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }
}

