package com.caremyhome.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceComment {

    @Column(name = "from_user")
    private String fromUser;

    private String text;
    private Date date = new Date();
    private LocalDateTime timestamp = LocalDateTime.now();

    public MaintenanceComment(String fromUser, String text) {
    }
}
