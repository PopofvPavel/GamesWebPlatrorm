package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer > {
    List<Rating> findByGameId(Integer gameId);

    List<Rating> findByUserId(Integer userId);
}
