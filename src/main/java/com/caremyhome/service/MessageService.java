package com.caremyhome.service;

import com.caremyhome.model.Message;
import com.caremyhome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepo;

    public List<Message> getInbox(String email) {
        return messageRepo.findByToEmailOrderBySentAtDesc(email);
    }

    public List<Message> getSent(String email) {
        return messageRepo.findByFromEmailOrderBySentAtDesc(email);
    }

    public Message sendMessage(Message msg) {
        msg.setSentAt(LocalDateTime.now());
        return messageRepo.save(msg);
    }

    public void deleteMessage(UUID id, String email) {
        // Only allow delete if sender or recipient is current user
        messageRepo.findById(id).ifPresent(msg -> {
            if (msg.getFromEmail().equals(email) || msg.getToEmail().equals(email)) {
                messageRepo.delete(msg);
            }
        });
    }
}
