package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.api.telegram.TelegramBot;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.TelegramService;
import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TelegramServiceImpl implements TelegramService {

    final
    UserService userService;
    final
    TelegramBot telegramBot;

    @Autowired
    public TelegramServiceImpl(UserService userService, TelegramBot telegramBot) {
        this.userService = userService;
        this.telegramBot = telegramBot;
    }

    @Override
    public Optional<User> findUserByChatId(Long chatId) {
        return userService.findUserByChatId(chatId);
    }

    @Override
    public void scheduleNotification(int userId, String message, LocalDateTime notificationTime) {
        telegramBot.scheduleNotification(userId, message, notificationTime);
    }
}
