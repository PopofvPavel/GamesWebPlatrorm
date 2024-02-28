package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    List<Rating> findByGameId(Integer gameId);

}
