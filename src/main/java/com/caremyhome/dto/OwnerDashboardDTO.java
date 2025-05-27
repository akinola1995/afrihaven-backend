package com.caremyhome.dto;

import com.caremyhome.model.*;
import java.util.*;

public class OwnerDashboardDTO {
    public String name;
    public List<Property> properties;
    public List<MaintenanceDTO> maintenance;
    public List<InquiryDTO> inquiries;
    public List<TenantAssignment> tenantAssignments;
    public List<RentPayment> rentUploads;
    public List<UnassignmentRequest> unassignmentRequests;

    public OwnerDashboardDTO(String name, List<Property> properties, List<MaintenanceDTO> maintenance,
                             List<InquiryDTO> inquiries, List<TenantAssignment> tenantAssignments,
                             List<RentPayment> rentUploads, List<UnassignmentRequest> unassignmentRequests) {
        this.name = name;
        this.properties = properties;
        this.maintenance = maintenance;
        this.inquiries = inquiries;
        this.tenantAssignments = tenantAssignments;
        this.rentUploads = rentUploads;
        this.unassignmentRequests = unassignmentRequests;
    }
}
