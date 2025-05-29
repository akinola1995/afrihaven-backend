package com.caremyhome.dto;

import com.caremyhome.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
}

