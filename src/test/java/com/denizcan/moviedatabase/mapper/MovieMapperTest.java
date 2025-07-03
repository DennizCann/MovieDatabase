package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.MovieDTO;
import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.model.MovieGenre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

class MovieMapperTest {

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setYear(2010);
        movie.setSynopsis("Rüya içinde rüya konseptini işleyen bilim kurgu filmi");
        movie.setBudget(new BigDecimal("160000000"));
        movie.setBoxOffice(new BigDecimal("836836967"));
        movie.setImdbRating(8.8);
        movie.setDuration(148);
        movie.setImageUrl("https://example.com/inception.jpg");
        movie.setPartOfSeries(false);
        movie.setSeriesName(null);
    }

    @Test
    void testToDTO_WithBasicFields() {
        // When
        MovieDTO dto = MovieMapper.toDTO(movie);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Inception", dto.getTitle());
        assertEquals(2010, dto.getYear());
        assertEquals(8.8, dto.getImdbRating());
        assertEquals("https://example.com/inception.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithGenres() {
        // Given
        Set<MovieGenre> genres = new HashSet<>();
        genres.add(MovieGenre.SCIENCE_FICTION);
        genres.add(MovieGenre.THRILLER);
        genres.add(MovieGenre.ACTION);
        movie.setGenre(genres);

        // When
        MovieDTO dto = MovieMapper.toDTO(movie);

        // Then
        assertEquals(3, dto.getGenres().size());
        assertTrue(dto.getGenres().contains("SCIENCE_FICTION"));
        assertTrue(dto.getGenres().contains("THRILLER"));
        assertTrue(dto.getGenres().contains("ACTION"));
    }

    @Test
    void testToDTO_WithEmptyGenres() {
        // Given
        movie.setGenre(new HashSet<>());

        // When
        MovieDTO dto = MovieMapper.toDTO(movie);

        // Then
        assertNotNull(dto.getGenres());
        assertEquals(0, dto.getGenres().size());
    }

    @Test
    void testToDTO_WithNullGenres() {
        // Given
        movie.setGenre(null);

        // When
        MovieDTO dto = MovieMapper.toDTO(movie);

        // Then
        assertNotNull(dto.getGenres());
        assertEquals(0, dto.getGenres().size());
    }

    @Test
    void testToDTO_WithAllGenres() {
        // Given
        Set<MovieGenre> genres = new HashSet<>();
        for (MovieGenre genre : MovieGenre.values()) {
            genres.add(genre);
        }
        movie.setGenre(genres);

        // When
        MovieDTO dto = MovieMapper.toDTO(movie);

        // Then
        assertEquals(MovieGenre.values().length, dto.getGenres().size());
        for (MovieGenre genre : MovieGenre.values()) {
            assertTrue(dto.getGenres().contains(genre.name()));
        }
    }
} 