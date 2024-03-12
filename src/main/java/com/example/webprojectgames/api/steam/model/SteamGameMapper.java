package com.example.webprojectgames.api.steam.model;

import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SteamGameMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM. yyyy", new Locale("ru", "RU"));


    public SteamGame mapSteamGame(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            String gameId = rootNode.fieldNames().next(); // Получаем первый (и единственный) ключ из объекта

            if (rootNode.get(gameId).get("success").asBoolean()) {//если в ответе пришло success, значит такая игра есть
                JsonNode gameData = rootNode.get(gameId).get("data");

                String title = gameData.get("name").asText();
                String description = gameData.get("detailed_description").asText();
   /*         String releaseDateString = gameData.get("release_date").get("date").asText();

            Date releaseDate = null;
            try {
                releaseDate = dateFormat.parse(releaseDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }*/

                List<String> platforms = new ArrayList<>();
                JsonNode platformsNode = gameData.get("platforms");
                if (platformsNode != null) {
                    platformsNode.fieldNames().forEachRemaining(platforms::add);
                }
                String developer = gameData.get("developers").get(0).asText(); // Предполагается, что разработчик - первый в списке
                String imageUrl = gameData.get("header_image").asText();

                return new SteamGame(title, description, null, platforms, developer, imageUrl);
            } else {
                throw new GameNotFoundException("Game not found on Steam with id " +gameId);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json");
            throw new RuntimeException("Error parsing json", e);
        }
    }

    public List<SteamGame> mapAllSteamGamesTitles(String json) {
        List<SteamGame> steamGames = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode appList = rootNode.get("applist").get("apps");

            if (appList.isArray()) {
                for (JsonNode appNode : appList) {
                    long appId = appNode.get("appid").asLong();
                    String appName = appNode.get("name").asText();
                    SteamGame steamGame = new SteamGame(appName, "", null, new ArrayList<>(), "", "");
                    steamGame.setSteamId(appId);
                    steamGames.add(steamGame);
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json");
            throw new RuntimeException("Error parsing json", e);
        }
        return steamGames;
    }

    public List<SteamReview> mapSteamReview(long gameId, String body) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(body);

            List<SteamReview> reviews = new ArrayList<>();
            JsonNode reviewNode = rootNode.get("reviews");
            if (reviewNode.isArray()) {
                for (JsonNode node : reviewNode) {
                    String username = node.get("author").get("steamid").asText();
                    long dateInSeconds = node.get("timestamp_created").asLong();
                    Date date = new Date(dateInSeconds * 1000);
                    String comment = node.get("review").asText();
                    SteamReview review = new SteamReview(gameId, username, comment, date);
                    reviews.add(review);
                }
            }
            return reviews;
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json");
            e.printStackTrace();
        }
        return Collections.emptyList();


    }


}
