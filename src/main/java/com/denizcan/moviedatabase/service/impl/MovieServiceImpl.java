package com.denizcan.moviedatabase.service.impl;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.repository.MovieRepository;
import com.denizcan.moviedatabase.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
} 