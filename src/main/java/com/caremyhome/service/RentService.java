package com.caremyhome.service;

import com.caremyhome.dto.RentDTO;
import com.caremyhome.model.Rent;
import com.caremyhome.repository.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RentService {

    @Autowired
    private RentRepository rentRepository;

    public List<RentDTO> getRentsForProperty(UUID propertyId) {
        return rentRepository.findByProperty_Id(propertyId).stream()
                .map(RentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void updateRentStatus(UUID id, String status) {
        Rent rent = rentRepository.findById(id).orElseThrow();
        rent.setStatus(status);
        rentRepository.save(rent);
    }
}
