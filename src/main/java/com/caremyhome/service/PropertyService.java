package com.caremyhome.service;

import com.caremyhome.dto.PropertyDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    // The base upload dir from your yml ("uploads")
    @Value("${afrihaven.uploads.dir}")
    private String uploadBaseDir;

    public Property createProperty(PropertyDTO dto) {
        Property prop = new Property();
        prop.setTitle(dto.getTitle());
        prop.setAddress(dto.getAddress());
        prop.setDescription(dto.getDescription());
        prop.setPrice(dto.getPrice());
        prop.setPropertyType(dto.getPropertyType());
        prop.setBedrooms(dto.getBedrooms());
        prop.setCountry(dto.getCountry());
        prop.setState(dto.getState());
        prop.setCity(dto.getCity());
        prop.setType(dto.getType());
        prop.setOwnerEmail(dto.getOwnerEmail());

        userRepo.findByEmail(dto.getOwnerEmail()).ifPresent(prop::setOwner);

        return propertyRepo.save(prop);
    }

    public Property createPropertyWithMedia(PropertyDTO dto, List<MultipartFile> images, MultipartFile video) throws IOException {
        Property prop = new Property();
        prop.setTitle(dto.getTitle());
        prop.setAddress(dto.getAddress());
        prop.setDescription(dto.getDescription());
        prop.setPrice(dto.getPrice());
        prop.setPropertyType(dto.getPropertyType());
        prop.setBedrooms(dto.getBedrooms());
        prop.setCountry(dto.getCountry());
        prop.setState(dto.getState());
        prop.setCity(dto.getCity());
        prop.setType(dto.getType());
        prop.setOwnerEmail(dto.getOwnerEmail());

        // --------- Ensure main upload directory exists ---------
        File baseDir = new File(uploadBaseDir).getAbsoluteFile();
        if (!baseDir.exists()) baseDir.mkdirs();

        // --------- Save images ---------
        File imageDir = new File(baseDir, "properties");
        if (!imageDir.exists()) imageDir.mkdirs();

        List<String> imageUrls = new ArrayList<>();
        if (images != null) {
            for (MultipartFile img : images) {
                if (!img.isEmpty()) {
                    String filename = UUID.randomUUID() + "_" + img.getOriginalFilename().replaceAll("\\s+", "_");
                    File dest = new File(imageDir, filename);
                    img.transferTo(dest);
                    // Use this URL for frontend image loading
                    imageUrls.add("/uploads/properties/" + filename);
                }
            }
        }
        prop.setImages(imageUrls);

        // --------- Save video (optional) ---------
        if (video != null && !video.isEmpty()) {
            File videoDir = new File(baseDir, "property-videos");
            if (!videoDir.exists()) videoDir.mkdirs();
            String vFilename = UUID.randomUUID() + "_" + video.getOriginalFilename().replaceAll("\\s+", "_");
            File vDest = new File(videoDir, vFilename);
            video.transferTo(vDest);
            prop.setVideoUrl("/uploads/property-videos/" + vFilename);
        }

        userRepo.findByEmail(dto.getOwnerEmail()).ifPresent(prop::setOwner);

        return propertyRepo.save(prop);
    }
}
