package com.caremyhome.model;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Comment {
    private String from;
    private String text;
    private LocalDateTime date;

    public Comment() {}

    public Comment(String from, String text) {
        this.from = from;
        this.text = text;
        this.date = LocalDateTime.now();
    }

    // Getters and Setters
}
