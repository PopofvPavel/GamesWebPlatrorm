package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.services.implementation.GameComparisonServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
/**
 * Долгий по выполнению тест(неск. десятков секунд),
 * который проверяет, что для каждого года приходит хотя бы одна игра
 */
class GameComparisonServiceIntegrationTest {

    @Autowired
    private GameComparisonServiceImpl gameComparisonService;

    @Test
    void testGetMatchingGamesForMultipleYears() {
        // Iterate through the years and call the getMatchingGames method with different years
        for (int year = 1998; year <= 2024; year++) {
            List<SteamGame> matchingGames = gameComparisonService.getMatchingGames(String.valueOf(year));
            assertNotNull(matchingGames, "Matching games list should not be null");
            assertFalse(matchingGames.isEmpty(), "Matching games list should not be empty");
        }
    }

}