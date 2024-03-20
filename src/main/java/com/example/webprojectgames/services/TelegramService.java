package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TelegramService {
    public Optional<User> findUserByChatId(Long chatId);



    void scheduleNotification(Notification notification, LocalDateTime notificationTime);

    boolean isUserAuthenticated(int userId);
}
