package com.caremyhome.repository;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
    List<Reminder> findByEmail(String email);
}
