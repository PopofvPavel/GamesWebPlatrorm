package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    List<Game> getAllGames();
    List<Game> getUserGamesCollection(int userId);

    Game findById(int id);

}
