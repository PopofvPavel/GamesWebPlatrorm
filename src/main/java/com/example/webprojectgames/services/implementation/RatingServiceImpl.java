package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Rating;
import com.example.webprojectgames.repositories.RatingRepository;
import com.example.webprojectgames.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    final
    RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findByGameId(Long gameId) {
        return ratingRepository.findByGameId(gameId);
    }

    @Override
    public List<Rating> findByUserId(Long userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public boolean hasUserRatedGame(long userId, long id) {
        return ratingRepository.findByUserId(userId)
                .stream().anyMatch((g -> g.getGameId() == id));
    }

    @Override
    public void rewriteRating(Rating rating) {
        Optional<Rating> oldRating = ratingRepository
                .findByUserId(rating.getUserId())
                .stream().filter(g -> g.getGameId() == rating.getGameId())
                .findFirst();
        oldRating.ifPresent(ratingRepository::delete);

        saveRating(rating);
    }
}
