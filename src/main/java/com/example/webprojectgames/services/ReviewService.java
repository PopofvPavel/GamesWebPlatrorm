package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.model.entities.ReviewInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public interface ReviewService {
    List<ReviewInterface> findByGameId(Integer gameId);

    void saveReview(Review review);
}
