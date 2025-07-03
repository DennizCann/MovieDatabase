package com.denizcan.moviedatabase.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

class MovieTest {

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    void testMovieCreation() {
        // Given
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setYear(2010);
        movie.setSynopsis("Rüya içinde rüya konseptini işleyen bilim kurgu filmi");
        movie.setBudget(new BigDecimal("160000000"));
        movie.setBoxOffice(new BigDecimal("836836967"));
        movie.setImdbRating(8.8);
        movie.setDuration(148);
        movie.setImageUrl("https://example.com/inception.jpg");

        // When & Then
        assertEquals(1L, movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals(2010, movie.getYear());
        assertEquals("Rüya içinde rüya konseptini işleyen bilim kurgu filmi", movie.getSynopsis());
        assertEquals(new BigDecimal("160000000"), movie.getBudget());
        assertEquals(new BigDecimal("836836967"), movie.getBoxOffice());
        assertEquals(8.8, movie.getImdbRating());
        assertEquals(148, movie.getDuration());
        assertEquals("https://example.com/inception.jpg", movie.getImageUrl());
    }

    @Test
    void testMovieGenres() {
        // Given
        Set<MovieGenre> genres = new HashSet<>();
        genres.add(MovieGenre.SCIENCE_FICTION);
        genres.add(MovieGenre.THRILLER);
        genres.add(MovieGenre.ACTION);

        // When
        movie.setGenre(genres);

        // Then
        assertEquals(3, movie.getGenre().size());
        assertTrue(movie.getGenre().contains(MovieGenre.SCIENCE_FICTION));
        assertTrue(movie.getGenre().contains(MovieGenre.THRILLER));
        assertTrue(movie.getGenre().contains(MovieGenre.ACTION));
    }

    @Test
    void testSeriesInformation() {
        // Given
        movie.setPartOfSeries(true);
        movie.setSeriesName("Batman Trilogy");

        // When & Then
        assertTrue(movie.isPartOfSeries());
        assertEquals("Batman Trilogy", movie.getSeriesName());
    }

    @Test
    void testMovieEquality() {
        // Given
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Inception");

        Movie movie2 = new Movie();
        movie2.setId(1L);
        movie2.setTitle("Inception");

        // When & Then
        assertEquals(movie1.getId(), movie2.getId());
        assertEquals(movie1.getTitle(), movie2.getTitle());
    }
} 