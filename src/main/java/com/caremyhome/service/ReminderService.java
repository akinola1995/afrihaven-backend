package com.caremyhome.service;

import com.caremyhome.model.Reminder;
import com.caremyhome.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepo;

    public List<Reminder> getRemindersForEmail(String email) {
        return reminderRepo.findByEmailOrderByDateDesc(email);
    }

    public Reminder addReminder(Reminder reminder) {
        return reminderRepo.save(reminder);
    }
}
