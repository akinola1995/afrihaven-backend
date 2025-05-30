package com.caremyhome.controller;

import com.caremyhome.service.PropertySearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/properties")
public class PropertySearchController {
    @Autowired
    private PropertySearchService propertySearchService;

    // GET /api/properties/search?type=rent&country=Nigeria&state=Lagos&minPrice=50000&bedrooms=2
    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam Map<String, String> params) {
        return propertySearchService.search(params);
    }
}
