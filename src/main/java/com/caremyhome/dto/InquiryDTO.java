package com.caremyhome.dto;

import com.caremyhome.model.Inquiry;

public class InquiryDTO {
    private String propertyName;
    private String status;
    private String submittedAt;

    public InquiryDTO(String propertyName, String status, String submittedAt) {
        this.propertyName = propertyName;
        this.status = status;
        this.submittedAt = submittedAt;
    }

    public static InquiryDTO fromEntity(Inquiry inquiry) {
        return new InquiryDTO(
                inquiry.getProperty().getTitle(),
                inquiry.getStatus(),
                inquiry.getCreatedAt().toString()
        );

    }
    // Getters and Setters
}

