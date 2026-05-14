package ru.kpfu.itis.tourbookingsystemspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.tourbookingsystemspring.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByRecipientIdOrderByCreatedAtDesc(Long recipientId);

    long countByRecipientIdAndIsReadFalse(Long recipientId);
}