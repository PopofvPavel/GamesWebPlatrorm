package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.webprojectgames.model.entities.UserGameCollection;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserGamesCollectionRepository extends JpaRepository<UserGameCollection, Long> {
    //List<UserGameCollection> findByUserId(int userId);
}
