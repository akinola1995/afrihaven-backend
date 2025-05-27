package com.caremyhome.repository;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByToEmailOrderBySentAtDesc(String toEmail);
    List<Message> findByFromEmailOrderBySentAtDesc(String fromEmail);
}

