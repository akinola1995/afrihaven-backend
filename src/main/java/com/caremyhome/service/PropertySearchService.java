package com.caremyhome.service;

import com.caremyhome.model.Property;
import com.caremyhome.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PropertySearchService {
    @Autowired
    private PropertyRepository propertyRepo;

    public List<Map<String, Object>> search(Map<String, String> params) {
        Specification<Property> spec = Specification.where(null);

        if (params.containsKey("type"))
            spec = spec.and((root, q, cb) -> cb.equal(root.get("type"), params.get("type")));

        if (params.containsKey("country"))
            spec = spec.and((root, q, cb) -> cb.equal(root.get("country"), params.get("country")));

        if (params.containsKey("state"))
            spec = spec.and((root, q, cb) -> cb.equal(root.get("state"), params.get("state")));

        if (params.containsKey("city"))
            spec = spec.and((root, q, cb) -> cb.equal(root.get("city"), params.get("city")));

        if (params.containsKey("minPrice"))
            spec = spec.and((root, q, cb) -> cb.ge(root.get("price"), Double.parseDouble(params.get("minPrice"))));

        if (params.containsKey("maxPrice"))
            spec = spec.and((root, q, cb) -> cb.le(root.get("price"), Double.parseDouble(params.get("maxPrice"))));

        if (params.containsKey("bedrooms"))
            spec = spec.and((root, q, cb) -> cb.equal(root.get("bedrooms"), Integer.parseInt(params.get("bedrooms"))));

        List<Property> list = propertyRepo.findAll(spec);

        // Map to simple DTO for frontend
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
            map.put("imageUrl", prop.getImages());
            return map;
        }).collect(Collectors.toList());
    }
}
