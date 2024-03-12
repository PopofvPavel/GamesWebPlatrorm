package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Platform;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.UserGameCollection;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import com.example.webprojectgames.repositories.GamesRepository;
import com.example.webprojectgames.repositories.UserGamesCollectionRepository;
import com.example.webprojectgames.services.GameComparisonService;
import com.example.webprojectgames.services.GameService;
import com.example.webprojectgames.services.PlatformService;
import com.example.webprojectgames.services.SteamApiService;
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
        gamesRepository.saveAll(games);
    }

    @Override
    public void loadGamesFromSteamUsingWiki(String year, long editorId) {
        List<SteamGame> steamGames = gameComparisonService.getMatchingGames(year);
        List<Game> games = new ArrayList<>();
        for (SteamGame steamGame : steamGames) {
            try {
                SteamGame fullGame = steamApiService.getSteamGame(steamGame.getSteamId());
                Game game = new Game();

                game.setSteamId(fullGame.getSteamId());
                game.setTitle(fullGame.getTitle());
                game.setDescription(fullGame.getDescription());
                List<Platform> platforms = platformService.getPlatformsByNames(fullGame.getPlatform());
                game.setPlatform(platforms);
                game.setImageUrl(fullGame.getImage_url());
                game.setEditorId((int) editorId);
                game.setReleaseDate(fullGame.getReleaseDate());
                game.setDeveloper(fullGame.getDeveloper());

                games.add(game);
            } catch (GameNotFoundException exception) {
                logger.error(exception.getMessage());

            }
        }
        saveAllGames(games);

    }

}
