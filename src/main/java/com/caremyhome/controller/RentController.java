package com.caremyhome.controller;

@RestController
@RequestMapping("/api/rents")
public class RentController {

    @Autowired
    private RentService rentService;

    @GetMapping("/{propertyId}")
    public List<RentDTO> getRentsByProperty(@PathVariable UUID propertyId) {
        return rentService.getRentsForProperty(propertyId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRentStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        rentService.updateRentStatus(id, body.get("status"));
        return ResponseEntity.ok().build();
    }
}

