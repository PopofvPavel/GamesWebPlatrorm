package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webprojectgames.model.entities.UserGameCollection;

import java.util.List;

public interface UserGamesCollectionRepository extends JpaRepository<UserGameCollection, Integer> {
    //List<UserGameCollection> findByUserId(int userId);
}
