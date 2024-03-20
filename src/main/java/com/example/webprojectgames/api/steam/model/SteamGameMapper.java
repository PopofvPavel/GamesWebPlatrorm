package com.example.webprojectgames.api.steam.model;

import com.example.webprojectgames.model.entities.SteamGame;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.model.exceptions.GameNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Метод для маппинга объектов, связанных с игрой из стима,
 * которые приходят в формате JSON
 */
@Component
public class SteamGameMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM. yyyy", new Locale("ru", "RU"));

    /**
     * Преобразует JSON-строку в объект SteamGame.
     *
     * @param json JSON-строка, содержащая информацию об игре в формате JSON.
     * @return Объект SteamGame, содержащий информацию об игре.
     * @throws GameNotFoundException Если игра не найдена в Steam по указанному идентификатору.
     */
    public SteamGame mapSteamGame(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            String gameId = rootNode.fieldNames().next(); // Получаем первый (и единственный) ключ из объекта

            if (rootNode.get(gameId).get("success").asBoolean()) {//если в ответе пришло success, значит такая игра есть
                JsonNode gameData = rootNode.get(gameId).get("data");

                String title = gameData.get("name").asText();
                String description = parseDescription(gameData.get("detailed_description").asText());
                String releaseDateString = gameData.get("release_date").get("date").asText();

                Date releaseDate = null;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
                    releaseDate = dateFormat.parse(releaseDateString);
                } catch (ParseException e) {
                    logger.error("Error parsing release date:" + e.getMessage());
                }


                // Extract genres
                List<String> genres = new ArrayList<>();
                JsonNode genresNode = gameData.get("genres");
                if (genresNode != null && genresNode.isArray()) {
                    for (JsonNode genreNode : genresNode) {
                        String genreDescription = genreNode.get("description").asText();
                        genres.add(genreDescription);
                    }
                }

                System.out.println("Genres: " + genres);


                List<String> platforms = new ArrayList<>();
                JsonNode platformsNode = gameData.get("platforms");
                if (platformsNode != null) {
                    platformsNode.fieldNames().forEachRemaining(platforms::add);
                }
                String developer = gameData.get("developers").get(0).asText(); // Предполагается, что разработчик - первый в списке
                String imageUrl = gameData.get("header_image").asText();

                return new SteamGame(title, description, releaseDate, platforms, developer, imageUrl, genres);
            } else {
                throw new GameNotFoundException("Game not found on Steam with id " + gameId);
            }
        } catch (JsonProcessingException | NullPointerException e) {
            System.out.println("Error parsing json");
            throw new RuntimeException("Error parsing json", e);
        }
    }
    /**
     * Метод для очистки описания игры от HTML-тегов и специальных символов HTML.
     * @param detailedDescription Описание игры, которое содержит мусор.
     * @return Очищенное описание игры .
     */
    private String parseDescription(String detailedDescription) {
        // Удаление HTML-тегов
        String cleanDescription = detailedDescription.replaceAll("\\<.*?\\>", "");

        // Удаление специальных символов HTML
        cleanDescription = cleanDescription.replaceAll("&.*?;", "");

        // Удаление множественных пробелов и лишних пробельных символов
        cleanDescription = cleanDescription.replaceAll("\\s+", " ").trim();

        return cleanDescription;
    }

    /**
     * Преобразует JSON-строку, содержащую список игр, в список объектов SteamGame.
     * Этот метод нужен для тех JSON-ов, в которых приходит не вся информация
     * об игре, а только steamId и title. Это происходит, когда загружается много игр
     * за раз, а не по одной игре
     * @param json JSON-строка, содержащая список игр в формате JSON.
     * @return Список объектов SteamGame, представляющих игры.
     */
    public List<SteamGame> mapAllSteamGamesTitles(String json) {
        List<SteamGame> steamGames = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode appList = rootNode.get("applist").get("apps");

            if (appList.isArray()) {
                for (JsonNode appNode : appList) {
                    long appId = appNode.get("appid").asLong();
                    String appName = appNode.get("name").asText();
                    SteamGame steamGame = new SteamGame(appName, "", null, new ArrayList<>(), "", "", null);
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
    /**
     * Преобразует JSON-строку, содержащую отзывы о игре в Steam, в список объектов SteamReview.
     * @param gameId Идентификатор игры.
     * @param body   JSON-строка, содержащая отзывы о игре.
     * @return Список объектов SteamReview, представляющих отзывы о игре.
     */
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
            logger.error("Error parsing release date:" + e.getMessage());
        }
        return Collections.emptyList();


    }


}
