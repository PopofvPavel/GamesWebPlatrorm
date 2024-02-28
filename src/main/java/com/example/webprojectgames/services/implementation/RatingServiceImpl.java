package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Rating;
import com.example.webprojectgames.repositories.RatingRepository;
import com.example.webprojectgames.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    final
    RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findByGameId(Integer gameId) {
        return ratingRepository.findByGameId(gameId);
    }
}
