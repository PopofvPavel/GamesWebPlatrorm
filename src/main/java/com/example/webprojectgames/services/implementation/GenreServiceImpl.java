package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Genre;
import com.example.webprojectgames.repositories.GenreRepository;
import com.example.webprojectgames.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;


    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }


    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getGenresByDescription(List<String> descriptions) {
        return genreRepository.findAllByDescriptionIn(descriptions);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> getGenresByDescriptionIgnoreCase(List<String> genreDescriptions) {
        return genreRepository.findAllByDescriptionInIgnoreCase(genreDescriptions);
    }

    @Override
    public Optional<Genre> findById(Long genreId) {
        return  genreRepository.findById(genreId);
    }
}
