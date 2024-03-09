package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Review;
import com.example.webprojectgames.model.entities.ReviewInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<ReviewInterface> findByGameId(Integer gameId);
}
