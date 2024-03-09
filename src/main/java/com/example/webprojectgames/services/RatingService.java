package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    List<Rating> findByGameId(Long gameId);

    List<Rating> findByUserId(Long userId);


    void saveRating(Rating rating);

    boolean hasUserRatedGame(long userId, long id);

    void rewriteRating(Rating rating);

}
