package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.repository.MovieRepository;
import com.denizcan.moviedatabase.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie testMovie;

    @BeforeEach
    void setUp() {
        testMovie = new Movie();
        testMovie.setId(1L);
        testMovie.setTitle("Inception");
        testMovie.setYear(2010);
        testMovie.setSynopsis("Rüya içinde rüya konseptini işleyen bilim kurgu filmi");
        testMovie.setBudget(new BigDecimal("160000000"));
        testMovie.setBoxOffice(new BigDecimal("836836967"));
        testMovie.setImdbRating(8.8);
        testMovie.setDuration(148);
        testMovie.setImageUrl("https://example.com/inception.jpg");
    }

    @Test
    void testSaveMovie() {
        // Given
        when(movieRepository.save(any(Movie.class))).thenReturn(testMovie);

        // When
        Movie savedMovie = movieService.save(testMovie);

        // Then
        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getTitle());
        assertEquals(2010, savedMovie.getYear());
        verify(movieRepository, times(1)).save(testMovie);
    }

    @Test
    void testFindById_WhenMovieExists() {
        // Given
        when(movieRepository.findById(1L)).thenReturn(Optional.of(testMovie));

        // When
        Optional<Movie> foundMovie = movieService.findById(1L);

        // Then
        assertTrue(foundMovie.isPresent());
        assertEquals("Inception", foundMovie.get().getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenMovieDoesNotExist() {
        // Given
        when(movieRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Movie> foundMovie = movieService.findById(999L);

        // Then
        assertFalse(foundMovie.isPresent());
        verify(movieRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
        // Given
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Inception");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("The Dark Knight");

        List<Movie> movies = Arrays.asList(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        // When
        List<Movie> foundMovies = movieService.findAll();

        // Then
        assertEquals(2, foundMovies.size());
        assertEquals("Inception", foundMovies.get(0).getTitle());
        assertEquals("The Dark Knight", foundMovies.get(1).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(movieRepository).deleteById(1L);

        // When
        movieService.deleteById(1L);

        // Then
        verify(movieRepository, times(1)).deleteById(1L);
    }
} 