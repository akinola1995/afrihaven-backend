package com.caremyhome.controller;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin
public class ReminderController {

    @Autowired
    private ReminderRepository reminderRepository;

    @GetMapping
    public ResponseEntity<List<Reminder>> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(reminderRepository.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<Reminder> addReminder(@RequestBody Reminder reminder) {
        return ResponseEntity.ok(reminderRepository.save(reminder));
    }
}
