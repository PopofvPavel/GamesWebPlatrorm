package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.UserState;
import com.example.webprojectgames.repositories.UserStateRepository;
import com.example.webprojectgames.services.UserStateService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserStateServiceImp  implements UserStateService {
    final
    UserStateRepository userStateRepository;

    public UserStateServiceImp(UserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }


    @Override
    public Optional<UserState> findUserStateByChatId(Long chatId) {
        return userStateRepository.findById(chatId);

    }

    @Override
    public Optional<UserState> findUserStateByUserId(Long userId) {
        return userStateRepository.findUserStateByUserId(userId);
    }

    @Override
    public void updateUserState(Long chatId, String state) {
        Optional<UserState> userStateOptional = userStateRepository.findById(chatId);
        UserState userState = userStateOptional.orElseGet(UserState::new);
            userState.setChatId(chatId);
            userState.setState(state);
            userStateRepository.save(userState);

       /* userState.ifPresent(usrState -> usrState.setState(state));*/

    }

    @Override
    public void saveCode(Long chatId, String code) {
        Optional<UserState> userStateOptional = userStateRepository.findById(chatId);
        if (userStateOptional.isPresent()) {
            UserState userState = userStateOptional.get();
            userState.setCode(code);
            userStateRepository.save(userState);
        }
    }


}
