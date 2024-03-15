package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Role;
import com.example.webprojectgames.model.entities.User;
import com.example.webprojectgames.model.entities.UserGameCollection;
import com.example.webprojectgames.repositories.RoleRepository;
import com.example.webprojectgames.repositories.UserGamesCollectionRepository;
import com.example.webprojectgames.repositories.UserRepository;
import com.example.webprojectgames.repositories.GamesRepository;
import com.example.webprojectgames.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private GamesRepository gamesRepository;

    private UserGamesCollectionRepository userGamesCollectionRepository;

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserGamesCollectionRepository(UserGamesCollectionRepository userGamesCollectionRepository) {
        this.userGamesCollectionRepository = userGamesCollectionRepository;
    }

    @Autowired
    public void setGamesRepository(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public void saveGameToUserCollection(UserGameCollection userGameCollection) {
        userGamesCollectionRepository.save(userGameCollection);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepository.getRoleByRoleName(roleName);
    }


}
