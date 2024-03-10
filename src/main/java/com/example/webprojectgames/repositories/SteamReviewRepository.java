package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.ReviewInterface;
import com.example.webprojectgames.model.entities.SteamReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SteamReviewRepository extends JpaRepository<SteamReview,Long> {
    List<ReviewInterface> findByGameId(long gameId);

}
