package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Rating;
import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.repositories.ReviewRepository;
import com.example.webprojectgames.services.RatingService;
import com.example.webprojectgames.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    final
    ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findByGameId(Integer gameId) {
        return reviewRepository.findByGameId(gameId);
    }
}
