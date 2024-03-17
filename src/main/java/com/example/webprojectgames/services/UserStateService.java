package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.UserState;

import java.util.Optional;

public interface UserStateService {
    Optional<UserState> findUserStateByChatId(Long chatId);

    Optional<UserState> findUserStateByUserId(Long userId);

    void updateUserState(Long chatId, String state);
}