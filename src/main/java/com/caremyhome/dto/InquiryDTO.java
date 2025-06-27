package com.caremyhome.dto;

import com.caremyhome.model.Inquiry;
import lombok.Data;

@Data
public class InquiryDTO {
    private String name;
    private String email;
    private String phone;
    private String message;
    private String submittedAt; // ISO string (optional)
}
