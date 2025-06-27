//package com.caremyhome.service;
//
//import com.caremyhome.dto.*;
//import com.caremyhome.model.*;
//import com.caremyhome.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class OwnerService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PropertyRepository propertyRepository;
//
//    @Autowired
//    private MaintenanceRequestRepository maintenanceRequestRepository;
//
//    @Autowired
//    private InquiryRepository inquiryRepository;
//
//    @Autowired
//    private TenantAssignmentRepository tenantAssignmentRepository;
//
//    @Autowired
//    private RentPaymentRepository rentPaymentRepository;
//
//    @Autowired
//    private UnassignmentRequestRepository unassignmentRequestRepository;
//
//    public OwnerDashboardDTO getOwnerDashboard(String email) {
//        User owner = userRepository.findByEmail(email).orElseThrow();
//        List<Property> properties = propertyRepository.findByOwner(owner);
//
//        List<MaintenanceDTO> maintenance = maintenanceRequestRepository
//                .findByPropertyIn(properties)
//                .stream()
//                .map(MaintenanceDTO::fromEntity)
//                .collect(Collectors.toList());
//
//        List<InquiryDTO> inquiries = inquiryRepository
//                .findByPropertyIn(properties)
//                .stream()
//                .map(InquiryDTO::fromEntity)
//                .collect(Collectors.toList());
//
//        List<TenantAssignment> tenantAssignments = tenantAssignmentRepository.findByOwner_Id(owner.getId());
//        List<RentPayment> rentUploads = rentPaymentRepository.findByOwner(owner.getId());
//        List<UnassignmentRequest> unassignmentRequests = unassignmentRequestRepository.findByOwner(owner.getId());
//
//        return new OwnerDashboardDTO(owner.getFullName(), properties, maintenance, inquiries, tenantAssignments, rentUploads, unassignmentRequests);
//    }
//}
