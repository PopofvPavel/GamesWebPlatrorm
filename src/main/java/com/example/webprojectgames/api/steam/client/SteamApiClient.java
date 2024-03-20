package com.example.webprojectgames.api.steam.client;


import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.api.steam.model.SteamGameMapper;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Класс для интеграции с API Steam с использованием REST.
 */
@Component
public class SteamApiClient {


    final SteamGameMapper steamGameMapper;

    private final RestTemplate restTemplate;


    public SteamApiClient(SteamGameMapper steamGameMapper) {
        this.restTemplate = new RestTemplate();
        this.steamGameMapper = steamGameMapper;
    }
    /**
     * Получает описание игры из Steam по её идентификатору.
     * @param appId id игры в Steam.
     * @return объект игры.
     * @throws GameNotFoundException Если игра не найдена в библиотеке Steam.
     */
    public SteamGame getGameDescription(long appId) {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appId;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        //return  response.getBody();
        SteamGame steamGame = steamGameMapper.mapSteamGame(response.getBody());
        if (steamGame == null) {
            throw new GameNotFoundException("Game with id " + appId + " not found in Steam library");
        }

        return steamGame;
    }
    /**
     * Получает отзывы на игру из Steam.
     * @param steamId id игры в Steam.
     * @param gameId  id игры в нашей базе данных.
     * @return Список отзывов на игру.
     * @throws GameNotFoundException Если отзывы не найдены для указанной игры в Steam.
     */
    public List<SteamReview> getGameReviews(long steamId, long gameId) {
        String url = "https://store.steampowered.com/appreviews/" + steamId + "?json=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<SteamReview> steamReviews = steamGameMapper.mapSteamReview(gameId, response.getBody());
        if (steamReviews == null || steamReviews.isEmpty()) {
            throw new GameNotFoundException("Reviews not found for game with Steam ID " + steamId);
        }
        return steamReviews;
    }
    /**
     * Получает список всех игр из Steam.
     * Используется для автоматической массовой загрузки
     * @return Список всех игр.
     */
    public List<SteamGame> getAllGames() {
        String url = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return steamGameMapper.mapAllSteamGamesTitles(response.getBody());
    }


}