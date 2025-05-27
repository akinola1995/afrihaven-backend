package com.caremyhome.service;

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
