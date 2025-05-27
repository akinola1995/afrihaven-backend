package com.caremyhome.service;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepo;

    @Autowired
    private PropertyRepository propertyRepo;

    public TenantDTO getTenantDashboard(String email) {
        Tenant tenant = tenantRepo.findByEmail(email).orElseThrow();
        Property property = tenant.getProperty();

        TenantDTO dto = new TenantDTO();
        dto.setName(tenant.getFullName());
        dto.setEmail(tenant.getEmail());
        dto.setProperty(new TenantDTO.PropertyDTO(property));
        dto.setMaintenanceRequests(property.getMaintenanceRequests());

        return dto;
    }
}

