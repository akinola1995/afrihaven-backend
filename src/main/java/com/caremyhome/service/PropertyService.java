package com.caremyhome.service;

import com.caremyhome.dto.PropertyDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.User;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepo;

    @Autowired
    private UserRepository userRepo;

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
        prop.setImageUrl(dto.getImageUrl());
        prop.setOwnerEmail(dto.getOwnerEmail());

        // Optional: assign real User/Owner
        User owner = userRepo.findByEmail(dto.getOwnerEmail());
        if (owner != null) {
            prop.setOwner(owner);
        }

        return propertyRepo.save(prop);
    }
}