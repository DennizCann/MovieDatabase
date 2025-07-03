package com.denizcan.moviedatabase.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

class ActorTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor();
    }

    @Test
    void testActorCreation() {
        // Given
        actor.setId(1L);
        actor.setName("Leonardo DiCaprio");
        actor.setImageUrl("https://example.com/dicaprio.jpg");

        // When & Then
        assertEquals(1L, actor.getId());
        assertEquals("Leonardo DiCaprio", actor.getName());
        assertEquals("https://example.com/dicaprio.jpg", actor.getImageUrl());
    }

    @Test
    void testActorWithAwards() {
        // Given
        Set<Award> awards = new HashSet<>();
        Award award1 = new Award();
        award1.setId(1L);
        award1.setName(AwardTitle.OSCAR);
        award1.setYear(2020);
        award1.setCategory(AwardCategory.BEST_ACTOR);
        awards.add(award1);

        // When
        actor.setAwards(awards);

        // Then
        assertEquals(1, actor.getAwards().size());
        assertTrue(actor.getAwards().contains(award1));
    }

    @Test
    void testActorWithMovies() {
        // Given
        Set<Movie> movies = new HashSet<>();
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Inception");
        movies.add(movie1);

        // When
        actor.setMovies(movies);

        // Then
        assertEquals(1, actor.getMovies().size());
        assertTrue(actor.getMovies().contains(movie1));
    }

    @Test
    void testActorEquality() {
        // Given
        Actor actor1 = new Actor();
        actor1.setId(1L);
        actor1.setName("Leonardo DiCaprio");

        Actor actor2 = new Actor();
        actor2.setId(1L);
        actor2.setName("Leonardo DiCaprio");

        // When & Then
        assertEquals(actor1.getId(), actor2.getId());
        assertEquals(actor1.getName(), actor2.getName());
    }
} 