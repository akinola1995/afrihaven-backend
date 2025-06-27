package com.caremyhome.repository;

import com.caremyhome.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByToEmailOrderBySentAtDesc(String toEmail);     // Inbox
    List<Message> findByFromEmailOrderBySentAtDesc(String fromEmail); // Sent
}
