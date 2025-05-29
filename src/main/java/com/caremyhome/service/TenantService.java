package com.caremyhome.service;

import com.caremyhome.dto.TenantDTO;
import com.caremyhome.model.Property;
import com.caremyhome.model.Tenant;
import com.caremyhome.repository.PropertyRepository;
import com.caremyhome.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepo;

    @Autowired
    private PropertyRepository propertyRepo;

    public TenantDTO getTenantDashboard(String email) {
        Tenant tenant = tenantRepo.findByEmail(email).orElseThrow();
        Property property = tenant.getAssignedProperty();

        TenantDTO dto = new TenantDTO();
        dto.setName(tenant.getFullName());
        dto.setEmail(tenant.getEmail());
        dto.setProperty(new TenantDTO.PropertyDTO(property));
        dto.setMaintenanceRequests(property.getMaintenanceRequests());

        return dto;
    }
}

