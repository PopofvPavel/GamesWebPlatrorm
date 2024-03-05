package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsername(String username);



}
