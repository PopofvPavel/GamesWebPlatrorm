package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    List<Rating> findByGameId(Integer gameId);

    List<Rating> findByUserId(Integer userId);


    void saveRating(Rating rating);

    boolean hasUserRatedGame(int userId, int id);

    void rewriteRating(Rating rating);

}
