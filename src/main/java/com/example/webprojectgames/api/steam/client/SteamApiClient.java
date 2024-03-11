package com.example.webprojectgames.api.steam.client;


import com.example.webprojectgames.model.entities.ReviewInterface;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.api.steam.model.SteamGameMapper;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SteamApiClient {

  /*  @Value("${steam.api.key}") //described in properties
    private String apiKey;*/

    final SteamGameMapper steamGameMapper;

    private final RestTemplate restTemplate;


    public SteamApiClient(SteamGameMapper steamGameMapper) {
        this.restTemplate = new RestTemplate();
        this.steamGameMapper = steamGameMapper;
    }

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

    public List<SteamReview> getGameReviews(long steamId, long gameId) {
        String url = "https://store.steampowered.com/appreviews/" + steamId + "?json=1";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<SteamReview> steamReviews = steamGameMapper.mapSteamReview(gameId, response.getBody());
        if (steamReviews == null || steamReviews.isEmpty()) {
            throw new GameNotFoundException("Reviews not found for game with Steam ID " + steamId);
        }
        return steamReviews;
    }

}