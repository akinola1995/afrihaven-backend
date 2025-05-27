package com.caremyhome.controller;

@RestController
@RequestMapping("/api/inquirer")
public class InquirerController {

    private final InquiryRepository inquiryRepository;

    public InquirerController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/inquiries")
    public List<InquiryDTO> getInquiriesByEmail(@RequestParam String email) {
        return inquiryRepository.findByEmail(email).stream()
                .map(inq -> new InquiryDTO(
                        inq.getProperty().getTitle(),
                        inq.getStatus(),
                        inq.getSubmittedAt().toString()
                ))
                .collect(Collectors.toList());
    }
}
