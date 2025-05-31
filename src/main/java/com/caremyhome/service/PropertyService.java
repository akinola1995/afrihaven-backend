package com.caremyhome.service;

import com.caremyhome.dto.PropertyDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepo;
    @Autowired
    private UserRepository userRepo;

    // Legacy JSON-only (no file uploads)
    public Property createProperty(PropertyDTO dto) {
        Property prop = new Property();
        prop.setTitle(dto.getTitle());
        prop.setDescription(dto.getDescription());
        prop.setPrice(dto.getPrice());
        prop.setPropertyType(dto.getPropertyType());
        prop.setBedrooms(dto.getBedrooms());
        prop.setCountry(dto.getCountry());
        prop.setState(dto.getState());
        prop.setCity(dto.getCity());
        prop.setType(dto.getType());
        prop.setOwnerEmail(dto.getOwnerEmail());

        // Optional: you may add logic here to fetch/set a default image
        // but don't setImages(dto.getImages()) unless dto has them (rare for JSON)

        User owner = userRepo.findByEmail(dto.getOwnerEmail());
        if (owner != null) {
            prop.setOwner(owner);
        }

        return propertyRepo.save(prop);
    }

    // Modern: Create property with media (images, optional video)
    public Property createPropertyWithMedia(PropertyDTO dto, List<MultipartFile> images, MultipartFile video) throws IOException {
        Property prop = new Property();
        prop.setTitle(dto.getTitle());
        prop.setDescription(dto.getDescription());
        prop.setPrice(dto.getPrice());
        prop.setPropertyType(dto.getPropertyType());
        prop.setBedrooms(dto.getBedrooms());
        prop.setCountry(dto.getCountry());
        prop.setState(dto.getState());
        prop.setCity(dto.getCity());
        prop.setType(dto.getType());
        prop.setOwnerEmail(dto.getOwnerEmail());

        // Save images
        List<String> imageUrls = new ArrayList<>();
        String uploadDir = "uploads/properties";
        if (images != null) {
            for (MultipartFile img : images) {
                if (!img.isEmpty()) {
                    String filename = UUID.randomUUID() + "_" + img.getOriginalFilename();
                    File dest = new File(uploadDir, filename);
                    dest.getParentFile().mkdirs();
                    img.transferTo(dest);
                    imageUrls.add("/uploads/properties/" + filename);
                }
            }
        }
        prop.setImages(imageUrls);

        // Save video (optional)
        if (video != null && !video.isEmpty()) {
            String vFilename = UUID.randomUUID() + "_" + video.getOriginalFilename();
            File vDest = new File(uploadDir, vFilename);
            vDest.getParentFile().mkdirs();
            video.transferTo(vDest);
            prop.setVideoUrl("/uploads/properties/" + vFilename);
        }

        User owner = userRepo.findByEmail(dto.getOwnerEmail());
        if (owner != null) {
            prop.setOwner(owner);
        }

        return propertyRepo.save(prop);
    }
}
