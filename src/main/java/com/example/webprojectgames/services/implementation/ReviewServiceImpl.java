package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Rating;
import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.model.entities.ReviewInterface;
import com.example.webprojectgames.model.entities.SteamReview;
import com.example.webprojectgames.repositories.ReviewRepository;
import com.example.webprojectgames.repositories.SteamReviewRepository;
import com.example.webprojectgames.services.RatingService;
import com.example.webprojectgames.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    final
    ReviewRepository reviewRepository;

    final
    SteamReviewRepository steamReviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, SteamReviewRepository steamReviewRepository) {
        this.reviewRepository = reviewRepository;
        this.steamReviewRepository = steamReviewRepository;
    }

    @Override
    public List<ReviewInterface> findReviewByGameId(long gameId) {
        return reviewRepository.findByGameId(gameId);
    }

    @Override
    public List<ReviewInterface> findSteamReviewByGameId(long gameId) {

        return steamReviewRepository.findByGameId(gameId);
    }

    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void saveSteamReviews(List<SteamReview> steamReview) {
        steamReviewRepository.saveAll(steamReview);
    }
}
