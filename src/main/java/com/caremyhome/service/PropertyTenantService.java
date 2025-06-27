package com.caremyhome.service;

import com.caremyhome.model.PropertyTenantAssignment;
import com.caremyhome.model.ManualTenant;
import com.caremyhome.repository.PropertyTenantAssignmentRepository;
import com.caremyhome.repository.ManualTenantRepository;
import com.caremyhome.model.Property;
import com.caremyhome.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PropertyTenantService {

    @Autowired
    private PropertyTenantAssignmentRepository assignmentRepo;

    @Autowired
    private ManualTenantRepository manualTenantRepo;

    @Autowired
    private PropertyRepository propertyRepo;

    // Assigned Tenants (via Email/User)
    public List<Map<String, Object>> getAssignmentsByProperty(UUID propertyId) {
        List<PropertyTenantAssignment> assignments = assignmentRepo.findByPropertyId(propertyId);

        return assignments.stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("email", a.getTenant() != null ? a.getTenant().getEmail() : "");
            map.put("unit", a.getUnit());
            map.put("assignedAt", a.getAssignedAt());
            return map;
        }).collect(Collectors.toList());
    }

    // Manual Tenants
    public List<Map<String, Object>> getManualTenantsByProperty(UUID propertyId) {
        List<ManualTenant> tenants = manualTenantRepo.findByPropertyId(propertyId);

        return tenants.stream().map(t -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", t.getId());
            map.put("name", t.getName());
            map.put("unit", t.getUnit());
            map.put("phone", t.getPhone());
            return map;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> addManualTenant(UUID propertyId, String name, String unit, String phone) {
        Property property = propertyRepo.findById(propertyId).orElse(null);
        if (property == null) throw new RuntimeException("Property not found");

        ManualTenant tenant = new ManualTenant();
        tenant.setName(name);
        tenant.setUnit(unit);
        tenant.setPhone(phone);
        tenant.setProperty(property);
        manualTenantRepo.save(tenant);

        Map<String, Object> map = new HashMap<>();
        map.put("id", tenant.getId());
        map.put("name", tenant.getName());
        map.put("unit", tenant.getUnit());
        map.put("phone", tenant.getPhone());
        return map;
    }

    public void deleteManualTenant(UUID id) {
        manualTenantRepo.deleteById(id);
    }

    public void deleteAssignedTenant(UUID id) {
        assignmentRepo.deleteById(id);
    }
}
