package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long > {
    List<Rating> findByGameId(Long gameId);

    List<Rating> findByUserId(Long userId);
}
