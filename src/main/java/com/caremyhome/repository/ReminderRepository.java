package com.caremyhome.repository;

import com.caremyhome.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
    List<Reminder> findByEmail(String email);
}
