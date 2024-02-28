package com.guttery.madii.domain.notification.domain.repository;

import com.guttery.madii.domain.notification.domain.model.Notification;
import com.guttery.madii.domain.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserAndCreatedAtIsAfter(User user, LocalDateTime createdAt);
}
