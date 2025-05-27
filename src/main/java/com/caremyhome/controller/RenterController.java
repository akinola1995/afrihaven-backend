package com.caremyhome.controller;

import com.caremyhome.model.RenterInquiry;
import com.caremyhome.model.SavedProperty;
import com.caremyhome.model.User;
import com.caremyhome.repository.RenterInquiryRepository;
import com.caremyhome.repository.SavedPropertyRepository;
import com.caremyhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/renter")
public class RenterController {

    @Autowired
    private SavedPropertyRepository savedPropertyRepository;

    @Autowired
    private RenterInquiryRepository renterInquiryRepository;

    @Autowired
    private UserRepository userRepository;

    // GET renter's saved properties
    @GetMapping("/saved")
    public List<SavedProperty> getSavedProperties(Principal principal) {
        Optional<User> renter = userRepository.findByEmail(principal.getName());
        return renter.map(savedPropertyRepository::findByRenter).orElse(List.of());
    }

    // GET renter's inquiries
    @GetMapping("/inquiries")
    public List<RenterInquiry> getRenterInquiries(Principal principal) {
        Optional<User> renter = userRepository.findByEmail(principal.getName());
        return renter.map(renterInquiryRepository::findByRenter).orElse(List.of());
    }

    // POST save property
    @PostMapping("/save")
    public SavedProperty saveProperty(@RequestBody SavedProperty property, Principal principal) {
        User renter = userRepository.findByEmail(principal.getName()).orElseThrow();
        property.setRenter(renter);
        return savedPropertyRepository.save(property);
    }

    // POST new inquiry
    @PostMapping("/inquiry")
    public RenterInquiry submitInquiry(@RequestBody RenterInquiry inquiry, Principal principal) {
        User renter = userRepository.findByEmail(principal.getName()).orElseThrow();
        inquiry.setRenter(renter);
        return renterInquiryRepository.save(inquiry);
    }
}
