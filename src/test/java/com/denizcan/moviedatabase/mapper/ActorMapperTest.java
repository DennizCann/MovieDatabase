package com.denizcan.moviedatabase.mapper;

import com.denizcan.moviedatabase.dto.ActorDTO;
import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.model.Award;
import com.denizcan.moviedatabase.model.AwardCategory;
import com.denizcan.moviedatabase.model.AwardTitle;
import com.denizcan.moviedatabase.model.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class ActorMapperTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
        actor.setId(1L);
        actor.setName("Leonardo DiCaprio");
        actor.setImageUrl("https://example.com/leonardo.jpg");
    }

    @Test
    void testToDTO_WithBasicFields() {
        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNullImageUrl() {
        // Given
        actor.setImageUrl(null);

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertNull(dto.getImageUrl());
    }

    @Test
    void testToDTO_WithEmptyImageUrl() {
        // Given
        actor.setImageUrl("");

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithSpecialCharactersInName() {
        // Given
        actor.setName("Jean-Pierre Jeunet");

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Jean-Pierre Jeunet", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithLongName() {
        // Given
        actor.setName("Sir Anthony Hopkins CBE");

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Sir Anthony Hopkins CBE", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithDifferentId() {
        // Given
        actor.setId(999L);

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(999L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNullName() {
        // Given
        actor.setName(null);

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertNull(dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithEmptyName() {
        // Given
        actor.setName("");

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithAllNullFields() {
        // Given
        Actor nullActor = new Actor();
        nullActor.setId(null);
        nullActor.setName(null);
        nullActor.setImageUrl(null);

        // When
        ActorDTO dto = ActorMapper.toDTO(nullActor);

        // Then
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getImageUrl());
    }

    @Test
    void testToDTO_WithZeroId() {
        // Given
        actor.setId(0L);

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(0L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithNegativeId() {
        // Given
        actor.setId(-1L);

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(-1L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("https://example.com/leonardo.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithVeryLongImageUrl() {
        // Given
        actor.setImageUrl("https://very-long-url-example.com/very-long-path/to/very-long-image-name-with-many-characters-and-numbers-123456789.jpg");

        // When
        ActorDTO dto = ActorMapper.toDTO(actor);

        // Then
        assertEquals(1L, dto.getId());
        assertEquals("Leonardo DiCaprio", dto.getName());
        assertEquals("https://very-long-url-example.com/very-long-path/to/very-long-image-name-with-many-characters-and-numbers-123456789.jpg", dto.getImageUrl());
    }

    @Test
    void testToDTO_WithDifferentActors() {
        // Given
        Actor actor1 = new Actor();
        actor1.setId(1L);
        actor1.setName("Tom Hanks");
        actor1.setImageUrl("https://example.com/tom.jpg");

        Actor actor2 = new Actor();
        actor2.setId(2L);
        actor2.setName("Brad Pitt");
        actor2.setImageUrl("https://example.com/brad.jpg");

        // When
        ActorDTO dto1 = ActorMapper.toDTO(actor1);
        ActorDTO dto2 = ActorMapper.toDTO(actor2);

        // Then
        assertEquals(1L, dto1.getId());
        assertEquals("Tom Hanks", dto1.getName());
        assertEquals("https://example.com/tom.jpg", dto1.getImageUrl());

        assertEquals(2L, dto2.getId());
        assertEquals("Brad Pitt", dto2.getName());
        assertEquals("https://example.com/brad.jpg", dto2.getImageUrl());
    }
} 