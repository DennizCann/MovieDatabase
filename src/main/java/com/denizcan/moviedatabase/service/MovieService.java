package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie save(Movie movie);
    Optional<Movie> findById(Long id);
    List<Movie> findAll();
    void deleteById(Long id);
} 