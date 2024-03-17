package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    List<Game> getAllGames();
    List<Game> getUserGamesCollection(int userId);

    Game findById(long id);

    void saveGame(Game game);

    void loadGamesFromSteamUsingWiki(String year, long editorId);

    List<Game> searchGames(String query);

    List<Game> searchGamesByGenre(Long genreId);

    List<Game> searchGamesByGenreAndQuery(String query, Long genreId);
}
