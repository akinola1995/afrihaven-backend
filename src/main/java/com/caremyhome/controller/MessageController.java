package com.caremyhome.controller;

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

