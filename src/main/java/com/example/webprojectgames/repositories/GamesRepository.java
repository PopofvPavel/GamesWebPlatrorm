package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamesRepository extends JpaRepository<Game,Integer > {


}
