package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.api.steam.client.SteamApiClient;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.services.SteamApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SteamApiServiceImpl implements SteamApiService {
    private final SteamApiClient steamApiClient;

    @Autowired
    public SteamApiServiceImpl(SteamApiClient steamApiClient) {
        this.steamApiClient = steamApiClient;
    }

    @Override
    public SteamGame getSteamGame(long id) {
        return steamApiClient.getGameDescription(id);
    }

    public List<SteamReview> getSteamReviews(long steamId, long gameId) {
        return steamApiClient.getGameReviews(steamId,gameId);
    }
}
