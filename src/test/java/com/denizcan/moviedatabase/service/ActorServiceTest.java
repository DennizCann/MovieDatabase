package com.denizcan.moviedatabase.service;

import com.denizcan.moviedatabase.model.Actor;
import com.denizcan.moviedatabase.repository.ActorRepository;
import com.denizcan.moviedatabase.service.impl.ActorServiceImpl;
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
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Actor testActor;

    @BeforeEach
    void setUp() {
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Leonardo DiCaprio");
        testActor.setImageUrl("https://example.com/dicaprio.jpg");
    }

    @Test
    void testSaveActor() {
        // Given
        when(actorRepository.save(any(Actor.class))).thenReturn(testActor);

        // When
        Actor savedActor = actorService.save(testActor);

        // Then
        assertNotNull(savedActor);
        assertEquals("Leonardo DiCaprio", savedActor.getName());
        assertEquals("https://example.com/dicaprio.jpg", savedActor.getImageUrl());
        verify(actorRepository, times(1)).save(testActor);
    }

    @Test
    void testFindById_WhenActorExists() {
        // Given
        when(actorRepository.findById(1L)).thenReturn(Optional.of(testActor));

        // When
        Optional<Actor> foundActor = actorService.findById(1L);

        // Then
        assertTrue(foundActor.isPresent());
        assertEquals("Leonardo DiCaprio", foundActor.get().getName());
        verify(actorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_WhenActorDoesNotExist() {
        // Given
        when(actorRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Actor> foundActor = actorService.findById(999L);

        // Then
        assertFalse(foundActor.isPresent());
        verify(actorRepository, times(1)).findById(999L);
    }

    @Test
    void testFindAll() {
        // Given
        Actor actor1 = new Actor();
        actor1.setId(1L);
        actor1.setName("Leonardo DiCaprio");

        Actor actor2 = new Actor();
        actor2.setId(2L);
        actor2.setName("Tom Hanks");

        List<Actor> actors = Arrays.asList(actor1, actor2);
        when(actorRepository.findAll()).thenReturn(actors);

        // When
        List<Actor> foundActors = actorService.findAll();

        // Then
        assertEquals(2, foundActors.size());
        assertEquals("Leonardo DiCaprio", foundActors.get(0).getName());
        assertEquals("Tom Hanks", foundActors.get(1).getName());
        verify(actorRepository, times(1)).findAll();
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(actorRepository).deleteById(1L);

        // When
        actorService.deleteById(1L);

        // Then
        verify(actorRepository, times(1)).deleteById(1L);
    }
} 