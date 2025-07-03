package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Movie;
import com.denizcan.moviedatabase.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testGetAllMovies() throws Exception {
        // Given
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Inception");

        Movie movie2 = new Movie();
        movie2.setId(2L);
        movie2.setTitle("The Dark Knight");

        when(movieService.findAll()).thenReturn(Arrays.asList(movie1, movie2));

        // When & Then
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("The Dark Knight"));

        verify(movieService, times(1)).findAll();
    }

    @Test
    void testGetMovieById_WhenMovieExists() throws Exception {
        // Given
        when(movieService.findById(1L)).thenReturn(Optional.of(testMovie));

        // When & Then
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.year").value(2010))
                .andExpect(jsonPath("$.imdbRating").value(8.8));

        verify(movieService, times(1)).findById(1L);
    }

    @Test
    void testGetMovieById_WhenMovieDoesNotExist() throws Exception {
        // Given
        when(movieService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/movies/999"))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).findById(999L);
    }

    @Test
    void testCreateMovie() throws Exception {
        // Given
        when(movieService.save(any(Movie.class))).thenReturn(testMovie);

        // When & Then
        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"));

        verify(movieService, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie_WhenMovieExists() throws Exception {
        // Given
        when(movieService.findById(1L)).thenReturn(Optional.of(testMovie));
        when(movieService.save(any(Movie.class))).thenReturn(testMovie);

        // When & Then
        mockMvc.perform(put("/movies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMovie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"));

        verify(movieService, times(1)).findById(1L);
        verify(movieService, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie_WhenMovieDoesNotExist() throws Exception {
        // Given
        when(movieService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/movies/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testMovie)))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).findById(999L);
        verify(movieService, never()).save(any(Movie.class));
    }

    @Test
    void testDeleteMovie_WhenMovieExists() throws Exception {
        // Given
        when(movieService.findById(1L)).thenReturn(Optional.of(testMovie));
        doNothing().when(movieService).deleteById(1L);

        // When & Then
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).findById(1L);
        verify(movieService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMovie_WhenMovieDoesNotExist() throws Exception {
        // Given
        when(movieService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/movies/999"))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).findById(999L);
        verify(movieService, never()).deleteById(anyLong());
    }
} 