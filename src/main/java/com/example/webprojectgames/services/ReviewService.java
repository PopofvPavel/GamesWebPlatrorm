package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public interface ReviewService {
    List<Review> findByGameId(Integer gameId);

    void saveReview(Review review);
}
