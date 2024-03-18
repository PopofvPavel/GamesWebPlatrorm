package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndIsNotified(int userId, boolean notified);
}
