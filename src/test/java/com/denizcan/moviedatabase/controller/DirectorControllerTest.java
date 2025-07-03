package com.denizcan.moviedatabase.controller;

import com.denizcan.moviedatabase.model.Director;
import com.denizcan.moviedatabase.service.DirectorService;
import com.denizcan.moviedatabase.dto.DirectorDTO;
import com.denizcan.moviedatabase.mapper.DirectorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DirectorController.class)
class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Director testDirector;
    private DirectorDTO testDirectorDTO;

    @BeforeEach
    void setUp() {
        testDirector = new Director();
        testDirector.setId(1L);
        testDirector.setName("Christopher Nolan");
        testDirector.setImageUrl("https://example.com/nolan.jpg");

        testDirectorDTO = DirectorMapper.toDTO(testDirector);
    }

    @Test
    void testGetAllDirectors() throws Exception {
        // Given
        Director director1 = new Director();
        director1.setId(1L);
        director1.setName("Christopher Nolan");

        Director director2 = new Director();
        director2.setId(2L);
        director2.setName("Quentin Tarantino");

        when(directorService.findAll()).thenReturn(Arrays.asList(director1, director2));

        // When & Then
        mockMvc.perform(get("/directors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Christopher Nolan"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Quentin Tarantino"));

        verify(directorService, times(1)).findAll();
    }

    @Test
    void testGetDirectorById_WhenDirectorExists() throws Exception {
        // Given
        when(directorService.findById(1L)).thenReturn(Optional.of(testDirector));

        // When & Then
        mockMvc.perform(get("/directors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Christopher Nolan"))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/nolan.jpg"));

        verify(directorService, times(1)).findById(1L);
    }

    @Test
    void testGetDirectorById_WhenDirectorDoesNotExist() throws Exception {
        // Given
        when(directorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/directors/999"))
                .andExpect(status().isNotFound());

        verify(directorService, times(1)).findById(999L);
    }

    @Test
    void testCreateDirector() throws Exception {
        // Given
        when(directorService.save(any(Director.class))).thenReturn(testDirector);

        // When & Then
        mockMvc.perform(post("/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDirector)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Christopher Nolan"));

        verify(directorService, times(1)).save(any(Director.class));
    }

    @Test
    void testUpdateDirector_WhenDirectorExists() throws Exception {
        // Given
        when(directorService.findById(1L)).thenReturn(Optional.of(testDirector));
        when(directorService.save(any(Director.class))).thenReturn(testDirector);

        // When & Then
        mockMvc.perform(put("/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDirector)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Christopher Nolan"));

        verify(directorService, times(1)).findById(1L);
        verify(directorService, times(1)).save(any(Director.class));
    }

    @Test
    void testUpdateDirector_WhenDirectorDoesNotExist() throws Exception {
        // Given
        when(directorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(put("/directors/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDirector)))
                .andExpect(status().isNotFound());

        verify(directorService, times(1)).findById(999L);
        verify(directorService, never()).save(any(Director.class));
    }

    @Test
    void testDeleteDirector_WhenDirectorExists() throws Exception {
        // Given
        when(directorService.findById(1L)).thenReturn(Optional.of(testDirector));
        doNothing().when(directorService).deleteById(1L);

        // When & Then
        mockMvc.perform(delete("/directors/1"))
                .andExpect(status().isNoContent());

        verify(directorService, times(1)).findById(1L);
        verify(directorService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteDirector_WhenDirectorDoesNotExist() throws Exception {
        // Given
        when(directorService.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(delete("/directors/999"))
                .andExpect(status().isNotFound());

        verify(directorService, times(1)).findById(999L);
        verify(directorService, never()).deleteById(anyLong());
    }

    @Test
    void testCreateDirectorWithMinimalData() throws Exception {
        // Given
        Director minimalDirector = new Director();
        minimalDirector.setName("Steven Spielberg");
        // imageUrl null bırakıldı

        when(directorService.save(any(Director.class))).thenReturn(minimalDirector);

        // When & Then
        mockMvc.perform(post("/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(minimalDirector)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Steven Spielberg"));

        verify(directorService, times(1)).save(any(Director.class));
    }

    @Test
    void testGetAllDirectors_WhenNoDirectorsExist() throws Exception {
        // Given
        when(directorService.findAll()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/directors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(directorService, times(1)).findAll();
    }

    @Test
    void testUpdateDirectorWithDifferentData() throws Exception {
        // Given
        Director updatedDirector = new Director();
        updatedDirector.setName("Updated Director Name");
        updatedDirector.setImageUrl("https://example.com/updated.jpg");

        when(directorService.findById(1L)).thenReturn(Optional.of(testDirector));
        when(directorService.save(any(Director.class))).thenReturn(updatedDirector);

        // When & Then
        mockMvc.perform(put("/directors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDirector)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Director Name"))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/updated.jpg"));

        verify(directorService, times(1)).findById(1L);
        verify(directorService, times(1)).save(any(Director.class));
    }

    @Test
    void testCreateDirectorWithSpecialCharacters() throws Exception {
        // Given
        Director specialDirector = new Director();
        specialDirector.setName("Jean-Pierre Jeunet");
        specialDirector.setImageUrl("https://example.com/jeunet.jpg");

        when(directorService.save(any(Director.class))).thenReturn(specialDirector);

        // When & Then
        mockMvc.perform(post("/directors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(specialDirector)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jean-Pierre Jeunet"))
                .andExpect(jsonPath("$.imageUrl").value("https://example.com/jeunet.jpg"));

        verify(directorService, times(1)).save(any(Director.class));
    }
} 