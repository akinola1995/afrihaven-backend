package com.caremyhome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to AfriHaven");
        message.setText("Dear " + name + ",\n\nWelcome to AfriHaven! We're excited to have you.\n\nCheers,\nAfriHaven Team");

        mailSender.send(message);
    }
}
