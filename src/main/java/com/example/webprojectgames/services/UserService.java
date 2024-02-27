package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User getCurrentUser(String username);



}
