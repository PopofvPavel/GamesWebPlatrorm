package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<Game,Long > {


}
