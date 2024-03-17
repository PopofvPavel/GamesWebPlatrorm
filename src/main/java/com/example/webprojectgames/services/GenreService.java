package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Genre;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface GenreService {

    void save(Genre genre);

    List<Genre> getGenresByDescription(List<String> genres);

    List<Genre> getAllGenres();

    List<Genre> getGenresByDescriptionIgnoreCase(List<String> genreDescriptions);

    Optional<Genre> findById(Long genreId);
}
