package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.UserGameCollection;
import com.example.webprojectgames.repositories.GamesRepository;
import com.example.webprojectgames.repositories.UserGamesCollectionRepository;
import com.example.webprojectgames.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private GamesRepository gamesRepository;
    private UserGamesCollectionRepository userGamesCollectionRepository;

    @Autowired
    public void setUserGamesCollectionRepository(UserGamesCollectionRepository userGamesCollectionRepository) {
        this.userGamesCollectionRepository = userGamesCollectionRepository;
    }

    @Autowired
    public void setGamesRepository(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    @Override
    public List<Game> getUserGamesCollection(int userId) {
        List<UserGameCollection> userCollection = userGamesCollectionRepository.findAll();
        List<Integer> gameIds = userCollection.stream().filter(game -> game.getUserId() == userId)
                .map(UserGameCollection::getGameId).collect(Collectors.toList());
        return gamesRepository.findAllById(gameIds);

    }

}
