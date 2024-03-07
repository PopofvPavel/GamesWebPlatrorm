package com.example.webprojectgames.api.steam.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class SteamGameMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM. yyyy", new Locale("ru", "RU"));


    public SteamGame mapSteamGame(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            String gameId = rootNode.fieldNames().next(); // Получаем первый (и единственный) ключ из объекта
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
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing json");
            e.printStackTrace();
        }
        return null;
    }
}
