package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.User;

import java.util.Optional;

public interface TelegramService {
    public Optional<User> findUserByChatId(Long chatId);
}
