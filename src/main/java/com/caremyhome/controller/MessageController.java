package com.caremyhome.controller;

import com.caremyhome.model.Message;
import com.caremyhome.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    // Inbox
    @GetMapping("/inbox/{email}")
    public List<Message> inbox(@PathVariable String email) {
        return messageService.getInbox(email);
    }

    // Sent
    @GetMapping("/sent/{email}")
    public List<Message> sent(@PathVariable String email) {
        return messageService.getSent(email);
    }

    // Send new message (including reply)
    @PostMapping
    public Message sendMessage(@RequestBody Message msg) {
        return messageService.sendMessage(msg);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, @RequestParam String email) {
        messageService.deleteMessage(id, email);
    }
}
