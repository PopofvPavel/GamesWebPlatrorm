package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.UserState;

import java.util.Optional;

public interface UserStateService {
    Optional<UserState> findUserStateByChatId(Long chatId);

    Optional<UserState> findUserStateByUserId(Long userId);

    void updateUserState(Long chatId, String state);

    void saveCode(Long chatId, String code);

    String getTelegramCode(long userId);

    void setUserId(Long telegramChatId, long userId);

    void deleteState(Long telegramChatId);
}
