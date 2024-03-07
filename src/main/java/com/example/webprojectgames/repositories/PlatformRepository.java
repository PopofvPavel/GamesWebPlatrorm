package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Integer> {

    List<Platform> findAllByNameIn(List<String> platformNames);

}
