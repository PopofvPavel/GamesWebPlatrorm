package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Role;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserGameCollection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User findByUsername(String username);

    List<User> getAllUsers();

    void saveGameToUserCollection(UserGameCollection userGameCollection);

    void saveUser(User user);

    Role getRoleByRoleName(String roleName);

    void changeUserStatus(Integer userId);


    List<Role> getAllRoles();

    void updateUserRole(Integer userId, Integer roleId);

    Optional<User> findById(Integer userId);

    Optional<User> findUserByChatId(Long chatId);
}
