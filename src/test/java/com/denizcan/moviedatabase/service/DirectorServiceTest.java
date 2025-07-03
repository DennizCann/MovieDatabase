package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Director;
import com.denizcan.moviedatabase.repository.DirectorRepository;
import com.denizcan.moviedatabase.service.impl.DirectorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private DirectorServiceImpl directorService;

    private Director testDirector;

    @BeforeEach
    void setUp() {
        testDirector = new Director();
        testDirector.setId(1L);
        testDirector.setName("Christopher Nolan");
        testDirector.setImageUrl("https://example.com/nolan.jpg");
    }

    @Test
    void testSaveDirector() {
        // Given
        when(directorRepository.save(any(Director.class))).thenReturn(testDirector);

        // When
        Director savedDirector = directorService.save(testDirector);

        // Then
        assertNotNull(savedDirector);
        assertEquals("Christopher Nolan", savedDirector.getName());
        assertEquals("https://example.com/nolan.jpg", savedDirector.getImageUrl());
        verify(directorRepository, times(1)).save(testDirector);
    }

    @Test
    void testFindById_WhenDirectorExists() {
        // Given
        when(directorRepository.findById(1L)).thenReturn(Optional.of(testDirector));

        // When
        Optional<Director> foundDirector = directorService.findById(1L);

        // Then
        assertTrue(foundDirector.isPresent());
        assertEquals("Christopher Nolan", foundDirector.get().getName());
        verify(directorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenDirectorDoesNotExist() {
        // Given
        when(directorRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Director> foundDirector = directorService.findById(999L);

        // Then
        assertFalse(foundDirector.isPresent());
        verify(directorRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
        // Given
        Director director1 = new Director();
        director1.setId(1L);
        director1.setName("Christopher Nolan");

        Director director2 = new Director();
        director2.setId(2L);
        director2.setName("Quentin Tarantino");

        List<Director> directors = Arrays.asList(director1, director2);
        when(directorRepository.findAll()).thenReturn(directors);

        // When
        List<Director> foundDirectors = directorService.findAll();

        // Then
        assertEquals(2, foundDirectors.size());
        assertEquals("Christopher Nolan", foundDirectors.get(0).getName());
        assertEquals("Quentin Tarantino", foundDirectors.get(1).getName());
        verify(directorRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(directorRepository).deleteById(1L);

        // When
        directorService.deleteById(1L);

        // Then
        verify(directorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSaveDirectorWithNullValues() {
        // Given
        Director directorWithNullValues = new Director();
        directorWithNullValues.setName("Test Director");
        // imageUrl null bırakıldı
        
        when(directorRepository.save(any(Director.class))).thenReturn(directorWithNullValues);

        // When
        Director savedDirector = directorService.save(directorWithNullValues);

        // Then
        assertNotNull(savedDirector);
        assertEquals("Test Director", savedDirector.getName());
        assertNull(savedDirector.getImageUrl());
        verify(directorRepository, times(1)).save(directorWithNullValues);
    }

    @Test
    void testFindAll_WhenNoDirectorsExist() {
        // Given
        when(directorRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Director> foundDirectors = directorService.findAll();

        // Then
        assertNotNull(foundDirectors);
        assertTrue(foundDirectors.isEmpty());
        verify(directorRepository, times(1)).findAll();
    }
} 