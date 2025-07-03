package com.denizcan.moviedatabase.integration;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import com.denizcan.moviedatabase.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MovieIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MovieRepository movieRepository;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setTitle("Test Movie");
        testMovie.setYear(2024);
        testMovie.setSynopsis("Test film açıklaması");
        testMovie.setBudget(new BigDecimal("1000000"));
        testMovie.setBoxOffice(new BigDecimal("5000000"));
        testMovie.setImdbRating(7.5);
        testMovie.setDuration(120);
        testMovie.setImageUrl("https://example.com/test.jpg");
        testMovie.setPartOfSeries(false);
        testMovie.setSeriesName(null);

        Set<MovieGenre> genres = new HashSet<>();
        genres.add(MovieGenre.ACTION);
        genres.add(MovieGenre.DRAMA);
        testMovie.setGenre(genres);
    }

    @Test
    void testSaveAndFindMovie() {
        // Given
        Movie savedMovie = entityManager.persistAndFlush(testMovie);

        // When
        Optional<Movie> foundMovie = movieRepository.findById(savedMovie.getId());

        // Then
        assertTrue(foundMovie.isPresent());
        assertEquals("Test Movie", foundMovie.get().getTitle());
        assertEquals(2024, foundMovie.get().getYear());
        assertEquals(7.5, foundMovie.get().getImdbRating());
        assertEquals(2, foundMovie.get().getGenre().size());
    }

    @Test
    void testFindAllMovies() {
        // Given
        Movie movie1 = new Movie();
        movie1.setTitle("Test Movie 1");
        movie1.setYear(2024);
        movie1.setImdbRating(7.5);

        Movie movie2 = new Movie();
        movie2.setTitle("Test Movie 2");
        movie2.setYear(2023);
        movie2.setImdbRating(8.0);

        entityManager.persistAndFlush(movie1);
        entityManager.persistAndFlush(movie2);

        // When
        List<Movie> movies = movieRepository.findAll();

        // Then
        assertTrue(movies.size() >= 2);
        assertTrue(movies.stream().anyMatch(m -> "Test Movie 1".equals(m.getTitle())));
        assertTrue(movies.stream().anyMatch(m -> "Test Movie 2".equals(m.getTitle())));
    }

    @Test
    void testUpdateMovie() {
        // Given
        Movie savedMovie = entityManager.persistAndFlush(testMovie);
        savedMovie.setTitle("Updated Test Movie");
        savedMovie.setImdbRating(8.5);

        // When
        Movie updatedMovie = movieRepository.save(savedMovie);
        entityManager.flush();

        // Then
        Optional<Movie> foundMovie = movieRepository.findById(savedMovie.getId());
        assertTrue(foundMovie.isPresent());
        assertEquals("Updated Test Movie", foundMovie.get().getTitle());
        assertEquals(8.5, foundMovie.get().getImdbRating());
    }

    @Test
    void testDeleteMovie() {
        // Given
        Movie savedMovie = entityManager.persistAndFlush(testMovie);
        Long movieId = savedMovie.getId();

        // When
        movieRepository.deleteById(movieId);
        entityManager.flush();

        // Then
        Optional<Movie> foundMovie = movieRepository.findById(movieId);
        assertFalse(foundMovie.isPresent());
    }

    @Test
    void testMovieWithGenres() {
        // Given
        Set<MovieGenre> genres = new HashSet<>();
        genres.add(MovieGenre.COMEDY);
        genres.add(MovieGenre.ROMANCE);
        testMovie.setGenre(genres);

        Movie savedMovie = entityManager.persistAndFlush(testMovie);

        // When
        Optional<Movie> foundMovie = movieRepository.findById(savedMovie.getId());

        // Then
        assertTrue(foundMovie.isPresent());
        assertEquals(2, foundMovie.get().getGenre().size());
        assertTrue(foundMovie.get().getGenre().contains(MovieGenre.COMEDY));
        assertTrue(foundMovie.get().getGenre().contains(MovieGenre.ROMANCE));
    }

    @Test
    void testMovieSeriesInformation() {
        // Given
        testMovie.setPartOfSeries(true);
        testMovie.setSeriesName("Test Series");

        Movie savedMovie = entityManager.persistAndFlush(testMovie);

        // When
        Optional<Movie> foundMovie = movieRepository.findById(savedMovie.getId());

        // Then
        assertTrue(foundMovie.isPresent());
        assertTrue(foundMovie.get().isPartOfSeries());
        assertEquals("Test Series", foundMovie.get().getSeriesName());
    }
} 