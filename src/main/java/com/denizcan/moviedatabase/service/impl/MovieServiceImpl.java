package com.denizcan.moviedatabase.service.impl;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import com.denizcan.moviedatabase.repository.MovieRepository;
import com.denizcan.moviedatabase.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Arama metodları implementasyonları
    @Override
    public List<Movie> findByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Movie> findByYear(Integer year) {
        return movieRepository.findByYear(year);
    }

    @Override
    public List<Movie> findByYearRange(Integer startYear, Integer endYear) {
        return movieRepository.findByYearBetween(startYear, endYear);
    }

    @Override
    public List<Movie> findByMinRating(Double minRating) {
        return movieRepository.findByImdbRatingGreaterThanEqual(minRating);
    }

    @Override
    public List<Movie> findSeriesMovies() {
        return movieRepository.findByPartOfSeriesTrue();
    }

    @Override
    public List<Movie> findBySeriesName(String seriesName) {
        return movieRepository.findBySeriesName(seriesName);
    }

    @Override
    public List<Movie> findByGenre(MovieGenre genre) {
        return movieRepository.findByGenre(genre);
    }

    @Override
    public Page<Movie> findMoviesWithFilters(String title, Integer year, Double minRating, Pageable pageable) {
        return movieRepository.findMoviesWithFilters(title, year, minRating, pageable);
    }

    @Override
    public Page<Movie> findTopRatedMovies(Pageable pageable) {
        return movieRepository.findTopRatedMovies(pageable);
    }

    @Override
    public Page<Movie> findLatestMovies(Pageable pageable) {
        return movieRepository.findLatestMovies(pageable);
    }
} 