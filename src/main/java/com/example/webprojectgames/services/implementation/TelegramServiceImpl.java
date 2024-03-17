package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.services.TelegramService;
import com.example.webprojectgames.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelegramServiceImpl implements TelegramService {

    final
    UserService userService;

    public TelegramServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<User> findUserByChatId(Long chatId) {
       return  userService.findUserByChatId(chatId);
    }
}
