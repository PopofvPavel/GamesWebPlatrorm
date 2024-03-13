package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.*;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import com.example.webprojectgames.repositories.GamesRepository;
import com.example.webprojectgames.repositories.UserGamesCollectionRepository;
import com.example.webprojectgames.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private GamesRepository gamesRepository;
    private UserGamesCollectionRepository userGamesCollectionRepository;

    private GameComparisonService gameComparisonService;
    private SteamApiService steamApiService;

    private PlatformService platformService;

    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }


    @Autowired
    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }

    @Autowired

    public void setSteamApiService(SteamApiService steamApiService) {
        this.steamApiService = steamApiService;
    }

    @Autowired
    public void setGameComparisonService(GameComparisonService gameComparisonService) {
        this.gameComparisonService = gameComparisonService;
    }

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
        List<Long> gameIds = userCollection.stream().filter(game -> game.getUserId() == userId)
                .map(UserGameCollection::getGameId).collect(Collectors.toList());
        return gamesRepository.findAllById(gameIds);

    }

    @Override
    public Game findById(long id) {
        Optional<Game> optionalGame = gamesRepository.findById(id);
        return optionalGame.orElse(null);
    }

    @Override
    public void saveGame(Game game) {
        gamesRepository.save(game);
    }

    public void saveAllGames(List<Game> games) {
        for (Game game : games) {
            Optional<Game> existingGame = gamesRepository.findBySteamId(game.getSteamId());
            if (existingGame.isPresent()) {
                // Update the existing game with the new data
                Game existing = updateExistingGame(game, existingGame);

                gamesRepository.save(existing); //save method to update the existing game
            } else {
                // Save a new game if it doesn't exist
                gamesRepository.save(game);
            }
        }
    }

    private static Game updateExistingGame(Game game, Optional<Game> existingGame) {
        Game existing = existingGame.get();
        existing.setTitle(game.getTitle());
        existing.setDescription(game.getDescription());
        existing.setEditorId(game.getEditorId());
        existing.setDeveloper(game.getDeveloper());
        existing.setImageUrl(game.getImageUrl());
        existing.setReleaseDate(game.getReleaseDate());
        existing.setGenres(game.getGenres());
        return existing;
    }

    @Override
    public void loadGamesFromSteamUsingWiki(String year, long editorId) {
        List<SteamGame> steamGames = gameComparisonService.getMatchingGames(year);
        List<Game> games = new ArrayList<>();
        for (SteamGame steamGame : steamGames) {
            try {
                SteamGame fullGame = steamApiService.getSteamGame(steamGame.getSteamId());
                Game game = new Game();

                game.setSteamId(steamGame.getSteamId());
                game.setTitle(fullGame.getTitle());
                game.setDescription(fullGame.getDescription());
                List<Platform> platforms = platformService.getPlatformsByNames(fullGame.getPlatform());
                game.setPlatform(platforms);
                game.setImageUrl(fullGame.getImage_url());
                game.setEditorId((int) editorId);
                game.setReleaseDate(fullGame.getReleaseDate());
                game.setDeveloper(fullGame.getDeveloper());
                List<Genre> genres = genreService.getGenresByDescription(fullGame.getGenres());
                game.setGenres(genres);

                games.add(game);
            } catch (GameNotFoundException exception) {
                logger.error(exception.getMessage());

            }
        }
       /* for(Game game : games) {
            saveGame(game);
        }*/
        saveAllGames(games);

    }
}
