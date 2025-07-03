package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.DirectorDTO;
import com.denizcan.moviedatabase.model.Director;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DirectorMapperTest {

    private Director director;

    @BeforeEach
    void setUp() {
        director = new Director();
        director.setId(1L);
        director.setName("Christopher Nolan");
        director.setImageUrl("https://example.com/nolan.jpg");
    }

    @Test
    void testToDTO_WithBasicFields() {
        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNullImageUrl() {
        // Given
        director.setImageUrl(null);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertNull(dto.getImageUrl());
    }

    @Test
    void testToDTO_WithEmptyImageUrl() {
        // Given
        director.setImageUrl("");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithSpecialCharactersInName() {
        // Given
        director.setName("Jean-Pierre Jeunet");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Jean-Pierre Jeunet", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithLongName() {
        // Given
        director.setName("Sir Ridley Scott");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Sir Ridley Scott", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithDifferentId() {
        // Given
        director.setId(999L);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(999L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNullName() {
        // Given
        director.setName(null);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertNull(dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithEmptyName() {
        // Given
        director.setName("");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithAllNullFields() {
        // Given
        Director nullDirector = new Director();
        nullDirector.setId(null);
        nullDirector.setName(null);
        nullDirector.setImageUrl(null);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(nullDirector);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getImageUrl());
    }

    @Test
    void testToDTO_WithZeroId() {
        // Given
        director.setId(0L);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(0L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNegativeId() {
        // Given
        director.setId(-1L);

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(-1L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithVeryLongImageUrl() {
        // Given
        director.setImageUrl("https://very-long-url-example.com/very-long-path/to/very-long-image-name-with-many-characters-and-numbers-123456789.jpg");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Christopher Nolan", dto.getName());
        assertEquals("https://very-long-url-example.com/very-long-path/to/very-long-image-name-with-many-characters-and-numbers-123456789.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithDifferentDirectors() {
        // Given
        Director director1 = new Director();
        director1.setId(1L);
        director1.setName("Quentin Tarantino");
        director1.setImageUrl("https://example.com/tarantino.jpg");

        Director director2 = new Director();
        director2.setId(2L);
        director2.setName("Steven Spielberg");
        director2.setImageUrl("https://example.com/spielberg.jpg");

        // When
        DirectorDTO dto1 = DirectorMapper.toDTO(director1);
        DirectorDTO dto2 = DirectorMapper.toDTO(director2);

        // Then
        assertEquals(1L, dto1.getId());
        assertEquals("Quentin Tarantino", dto1.getName());
        assertEquals("https://example.com/tarantino.jpg", dto1.getImageUrl());

        assertEquals(2L, dto2.getId());
        assertEquals("Steven Spielberg", dto2.getName());
        assertEquals("https://example.com/spielberg.jpg", dto2.getImageUrl());
    }

    @Test
    void testToDTO_WithAccentedCharacters() {
        // Given
        director.setName("Pedro Almodóvar");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Pedro Almodóvar", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNumbersInName() {
        // Given
        director.setName("John Carpenter III");

        // When
        DirectorDTO dto = DirectorMapper.toDTO(director);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("John Carpenter III", dto.getName());
        assertEquals("https://example.com/nolan.jpg", dto.getImageUrl());
    }
} 