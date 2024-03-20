package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.api.telegram.TelegramBot;
import com.example.webprojectgames.model.entities.Notification;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserState;
import com.example.webprojectgames.services.TelegramService;
import com.example.webprojectgames.services.UserService;
import com.example.webprojectgames.services.UserStateService;
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

    private final UserStateService userStateService;

    @Autowired
    public TelegramServiceImpl(UserService userService, TelegramBot telegramBot, UserStateService userStateService) {
        this.userService = userService;
        this.telegramBot = telegramBot;
        this.userStateService = userStateService;
    }

    @Override
    public Optional<User> findUserByChatId(Long chatId) {
        return userService.findUserByChatId(chatId);
    }

    @Override
    public void scheduleNotification(Notification notification, LocalDateTime notificationTime) {
        telegramBot.scheduleNotification(notification, notificationTime);
    }

    @Override
    public boolean isUserAuthenticated(int userId) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            if (user.get().getTelegramChatId() == null) {
                return false;
            }
            UserState userState = userStateService.findUserStateByUserId((long)userId).orElse(null);
            return userState != null && userState.getState().equals("AUTHORIZED");
        }

        return false;
    }
}
