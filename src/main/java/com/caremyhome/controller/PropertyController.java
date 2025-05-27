package com.caremyhome.controller;

import com.caremyhome.dto.PropertyDTO;
import com.caremyhome.model.Property;
import com.caremyhome.service.PropertyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    // PropertyController.java
    @GetMapping("/properties")
    public List<Property> getPropertiesByType(@RequestParam(required = false) String type) {
        if (type != null) {
            return propertyRepository.findByTypeIgnoreCase(type);
        }
        return propertyRepository.findAll();
    }


    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(@RequestParam(required = false) String type) {
        List<Property> properties;

        if (type != null && !type.isBlank()) {
            properties = propertyRepository.findByTypeIgnoreCase(type);
        } else {
            properties = propertyRepository.findAll();
        }

        return ResponseEntity.ok(properties);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addProperty(
            @RequestPart("property") PropertyDTO dto,
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @RequestPart(value = "video", required = false) MultipartFile video
    ) {
        Optional<User> ownerOpt = userRepository.findByEmail(dto.getOwnerEmail());
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid owner email");
        }

        Property property = new Property();
        property.setTitle(dto.getTitle());
        property.setDescription(dto.getDescription());
        property.setPrice(dto.getPrice());
        property.setType(dto.getType());
        property.setPropertyType(dto.getPropertyType());
        property.setBedrooms(dto.getBedrooms());
        property.setState(dto.getState());
        property.setCity(dto.getCity());
        property.setCountry(dto.getCountry());
        property.setOwner(ownerOpt.get());

        // Save image files
        if (images != null && images.length > 0) {
            List<String> imagePaths = new ArrayList<>();
            for (MultipartFile file : images) {
                String savedPath = saveFile(file);  // Save and return relative path
                imagePaths.add(savedPath);
            }
            property.setImageUrl(String.join(",", imagePaths));
        }

        // Save video
        if (video != null && !video.isEmpty()) {
            String videoPath = saveFile(video);
            property.setVideoUrl(videoPath);
        }

        propertyRepository.save(property);
        return ResponseEntity.ok("Property created");
    }

    private final String uploadDir = "uploads/";

    private String saveFile(MultipartFile file) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }


//    @PostMapping
//    public Property create(@RequestBody Property entity) {
//        return service.save(entity);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Optional<Property> getPropertyById(@PathVariable UUID id) {
        return propertyRepository.findById(id);
    }

    @PostMapping("/{id}/inquiry")
    public Inquiry submitInquiry(@PathVariable UUID id, @RequestBody Inquiry inquiry) {
        Property property = propertyRepository.findById(id).orElseThrow();
        inquiry.setProperty(property);
        return inquiryRepository.save(inquiry);
    }


    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String bedrooms,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String state
    ) {
        return propertyRepository.findAll().stream()
                .filter(p -> type == null || p.getType().equalsIgnoreCase(type))
                .filter(p -> propertyType == null || p.getPropertyType().equalsIgnoreCase(propertyType))
                .filter(p -> minPrice == null || p.getPrice() >= Integer.parseInt(minPrice))
                .filter(p -> maxPrice == null || p.getPrice() <= Integer.parseInt(maxPrice))
                .filter(p -> bedrooms == null || p.getBedrooms() == Integer.parseInt(bedrooms))
                .filter(p -> country == null || p.getCountry().equalsIgnoreCase(country))
                .filter(p -> state == null || p.getState().equalsIgnoreCase(state))
                .toList();
    }
}
