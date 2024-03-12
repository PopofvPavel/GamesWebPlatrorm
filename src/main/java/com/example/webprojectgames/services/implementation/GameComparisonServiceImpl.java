package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.api.steam.client.SteamApiClient;
import com.example.webprojectgames.api.wiki.WikipediaParserService;
import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.services.GameComparisonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameComparisonServiceImpl implements GameComparisonService {
    private final SteamApiClient steamApiClient;
    private final WikipediaParserService wikipediaParserService;


    public GameComparisonServiceImpl(SteamApiClient steamApiClient, WikipediaParserService wikipediaParserService) {
        this.steamApiClient = steamApiClient;
        this.wikipediaParserService = wikipediaParserService;

    }

    public List<SteamGame> getAllGames() {
        return steamApiClient.getAllGames();
        //return steamGames.stream().map(SteamGame::getTitle).collect(Collectors.toList());
    }

    public List<String> extractGameTitlesFromWikipedia(String year) {
        return wikipediaParserService.extractGameTitles(year);
    }

    public List<SteamGame> getMatchingGames(String year) {
        List<SteamGame> allGames = getAllGames();
        List<String> wikiGames = extractGameTitlesFromWikipedia(year);

        Set<String> wikiGameTitles = new HashSet<>(wikiGames);

        return allGames.stream()
                .filter(game -> wikiGameTitles.contains(game.getTitle()))
                .collect(Collectors.toList());
    }
}