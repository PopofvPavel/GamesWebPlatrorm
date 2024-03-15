package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Role;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserGameCollection;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsername(String username);


    void saveGameToUserCollection(UserGameCollection userGameCollection);

    void saveUser(User user);

    Role getRoleByRoleName(String roleName);
}
