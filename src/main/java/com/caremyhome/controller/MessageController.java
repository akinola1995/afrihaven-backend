package com.caremyhome.controller;

import com.caremyhome.model.Message;
import com.caremyhome.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        message.setSentAt(LocalDateTime.now());
        return ResponseEntity.ok(messageRepository.save(message));
    }

    @GetMapping("/inbox/{email}")
    public List<Message> getInbox(@PathVariable String email) {
        return messageRepository.findByToEmailOrderBySentAtDesc(email);
    }

    @GetMapping("/sent/{email}")
    public List<Message> getSent(@PathVariable String email) {
        return messageRepository.findByFromEmailOrderBySentAtDesc(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID id) {
        messageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

