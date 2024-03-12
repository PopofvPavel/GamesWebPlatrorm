package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.SteamGame;

import java.util.List;

public interface GameComparisonService {
    List<String> extractGameTitlesFromWikipedia(String year);
    List<SteamGame> getAllGames();
    List<SteamGame> getMatchingGames(String year);
}
