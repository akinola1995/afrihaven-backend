package com.caremyhome.controller;

import com.caremyhome.model.Reminder;
import com.caremyhome.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
