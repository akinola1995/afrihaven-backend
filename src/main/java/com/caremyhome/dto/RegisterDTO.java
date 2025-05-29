package com.caremyhome.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role;
}
