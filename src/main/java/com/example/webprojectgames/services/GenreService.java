package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {

    void save(Genre genre);

    List<Genre> getGenresByDescription(List<String> genres);

    List<Genre> getAllGenres();

}
