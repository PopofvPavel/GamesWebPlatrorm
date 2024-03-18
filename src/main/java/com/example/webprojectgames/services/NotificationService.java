package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.User;

import java.util.List;

public interface NotificationService {
    public List<Notification> getUserUnsendNotifications(User user);

    void save(Notification notification);
}
