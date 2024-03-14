package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamesRepository extends JpaRepository<Game,Long > {

    Optional<Game> findBySteamId(Long steamId);


    List<Game> findByTitleContainingIgnoreCase(String query);

    List<Game> findByGenresContaining(Genre genres);


    List<Game> findByGenresContainingIgnoreCaseOrderByTitleAsc(String genre);
}
