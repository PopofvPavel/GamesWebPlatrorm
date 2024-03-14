package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findAllByDescriptionIn(List<String> descriptions);

    List<Genre> findAllByDescriptionInIgnoreCase(List<String> genreDescriptions);
}
