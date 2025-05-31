package com.caremyhome.service;

import com.caremyhome.dto.RentStatusDto;
import com.caremyhome.model.RentUpload;
import com.caremyhome.repository.RentUploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentUploadService {

    private final RentUploadRepository rentRepo;

    public List<RentStatusDto> getRentStatusByProperty(UUID propertyId) {
        List<RentUpload> rents = rentRepo.findByPropertyIdOrderByDueDateDesc(propertyId);
        return rents.stream().map(r -> {
            RentStatusDto dto = new RentStatusDto();
            dto.setId(r.getId());
            dto.setTenant(r.getTenant());
            dto.setAmount(r.getAmount());
            dto.setDueDate(r.getDueDate());
            dto.setStatus(r.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    public void updateRentStatus(UUID rentId, String status) {
        RentUpload rent = rentRepo.findById(rentId).orElseThrow();
        rent.setStatus(status);
        rentRepo.save(rent);
    }
}
