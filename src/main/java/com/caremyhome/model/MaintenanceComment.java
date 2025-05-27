package com.caremyhome.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Date;

@Embeddable
@Data
public class MaintenanceComment {

    private String from;
    private String text;
    private Date date = new Date();

    public MaintenanceComment() {}
    public MaintenanceComment(String from, String text) {
        this.from = from;
        this.text = text;
    }
}
