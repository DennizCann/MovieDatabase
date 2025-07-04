package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie save(Movie movie);
    Optional<Movie> findById(Long id);
    List<Movie> findAll();
    void deleteById(Long id);
    
    // Arama metodları
    List<Movie> findByTitle(String title);
    List<Movie> findByYear(Integer year);
    List<Movie> findByYearRange(Integer startYear, Integer endYear);
    List<Movie> findByMinRating(Double minRating);
    List<Movie> findSeriesMovies();
    List<Movie> findBySeriesName(String seriesName);
    List<Movie> findByGenre(MovieGenre genre);
    
    // Gelişmiş arama
    Page<Movie> findMoviesWithFilters(String title, Integer year, Double minRating, Pageable pageable);
    Page<Movie> findTopRatedMovies(Pageable pageable);
    Page<Movie> findLatestMovies(Pageable pageable);
} 