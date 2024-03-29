package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.model.entities.ReviewInterface;
import com.example.webprojectgames.model.entities.SteamReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    List<ReviewInterface> findReviewByGameId(long gameId);

    List<ReviewInterface> findSteamReviewByGameId(long gameId);

    void saveReview(Review review);

    void saveSteamReviews(List<SteamReview> steamReview);
}
