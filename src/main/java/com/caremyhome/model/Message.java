package com.caremyhome.model;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String fromEmail;
    private String fromName;
    private String toEmail;
    private String subject;
    private String message;
    private LocalDateTime sentAt;
}

